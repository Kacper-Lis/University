#include "OneWire.h"
#include "DallasTemperature.h"
#include "dotDevice.h"
#include "esp_deep_sleep.h"

const char* ssid = "Pfizer Nanochip 3275.22.12";
const char* password = "notgamesanimecat";
const char* ws = "ws://ec2-52-15-138-171.us-east-2.compute.amazonaws.com:1234";
const char* gid = "QlGEwMe6";

dotDevice server_con(ssid, password, ws);
OneWire oneWire(26);
DallasTemperature sensor(&oneWire);


void setup() {
  long timeBeginning = millis();
  Serial.begin(115200);
  server_con.connect();
  sensor.begin();
  Serial.println(millis());
  
  String jsonArray = "";
  float tempSum = 0;
  for(int i = 0; i < 16; i++) {
    if (i > 0) {
      jsonArray += ",";
    }
    sensor.requestTemperatures();
    float temp = sensor.getTempCByIndex(0);
    tempSum += temp;
    jsonArray += "{\"timestamp\" : "+String(millis() - timeBeginning)+", \"value\": "+String(temp)+"}"; 
  }
  
  String json = String("{\"device\": \"QlGEwMe6\",\"average\": "+String(tempSum/16.0)+",\"values\":[" + jsonArray + "]}");
  server_con.sendJSON(json);
  Serial.println(millis());
  delay(500);
  
  long sleepTime = 29000 - (millis() - timeBeginning);
  esp_sleep_enable_timer_wakeup(sleepTime * 1000);
  esp_deep_sleep_start();
}


void loop() {
}
