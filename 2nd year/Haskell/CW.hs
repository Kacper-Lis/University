-- Group number 78
-- Christian George Sanger - 947777
-- Chinnapat Jongthep - 1909148
-- Kacper Lisikiewicz - 1911309 

import Test.QuickCheck ()
import Data.Char ( isAlpha, toLower ) 
import System.Environment ( getArgs )

-- Question 1
average:: Float -> Float -> Float -> Float
average x y z = (x + y + z) / 3

howManyAboveAverage1 :: Float -> Float -> Float -> Int
howManyAboveAverage1 x y z = length [a| a <- [x,y,z], a > average x y z]

howManyAboveAverage2 :: Num a => Float -> Float -> Float -> a
howManyAboveAverage2 x y z =
    isX + isY + isZ
    where
        isX = boolToInt $ x > avg 
        isY = boolToInt $ y > avg
        isZ = boolToInt $ z > avg
        avg = average x y z

boolToInt :: (Num a) => Bool -> a
boolToInt True = 1
boolToInt False = 0

--- howManyAboveAverage1 9 4 7 => 1
--- howManyAboveAverage2 1 9 1 => 1
--- howManyAboveAverage1 9 1 4 => 1
--- howManyAboveAverage2 7 7 7 => 0

listAverage :: [Float] -> Float
listAverage [] = 0
listAverage xs = sum xs / fromIntegral (length xs)

howManyAboveAverageL :: [Float] -> Int
howManyAboveAverageL xs = length (filter (>avg) xs)
                     where
                       avg = listAverage xs

checkCorrectness :: Float -> Float -> Float -> Bool
checkCorrectness x y z =
   howManyAboveAverage1 x y z == howManyAboveAverage2 x y z
-- +++ OK, passed 100 tests.


-- Question 2

split1 :: (a -> Bool) -> [a] -> ([a], [a])
split1 p xs = (true, false)
    where 
        true = [x | x <-xs, (p x)]
        false = [x | x <-xs, not (p x)]

split2 :: (a -> Bool) -> [a] -> ([a], [a])
split2 p xs = ((filter p xs), (filter (not.p) xs))

split3 :: (a -> Bool) -> [a] -> ([a], [a])
split3 _ [] = ([], [])
split3 p (x:xs) 
    | p x = (x:trueList, falseList)
    | otherwise = (trueList, x:falseList)
    where
        (trueList, falseList) = split3 p xs

testList = [4,5,2,6,5,8,66]

-- TestCases
-- split1 (<7) testList => ([4,5,2,6,5],[8,66])
-- split2 (<7) testList => ([4,5,2,6,5],[8,66])
-- split3 (<7) testList => ([4,5,2,6,5],[8,66])


-- Question 3
type Pizza = ([String], Float)

alfredo :: Pizza -> Float
alfredo (toppings, size) = 1.6 * (topping + base)
    where
        base = (area * baseCost)
        topping = (toppingCost * area *).fromIntegral.length $ toppings 
        area = pi * ((size /2) ** 2)
        toppingCost = 0.002
        baseCost = 0.001

bambini :: Pizza
bambini = (["tomatoes", "mozzarella", "ham", "salami", "broccoli", "mushrooms"], 14)
famiflia :: Pizza
famiflia = (["tomatoes", "mozzarella"], 32)

--Bambini => 3.2019
--Famiflia => 6.4339

pizzaCompare :: Pizza -> Pizza -> Bool
pizzaCompare p1 p2 = alfredo p1 > alfredo p2

--pizzaCompare bambini famiflia => False


-- Question 4

divides :: Int -> Int -> Bool
divides x y = y `mod` x == 0

prime :: Int -> Bool
prime n = n > 1 &&  and [not(divides x n) | x <- [2..(n-1)]]

allPrimes :: [Int]
allPrimes = [x | x <- [2..], prime x]

allPrimesBetween :: Int -> Int -> [Int]
allPrimesBetween start end = [x | x <- [start..end], prime x]

primeTest :: [Bool]
primeTest = map prime [0..]

primeTestPairs :: [(Integer, Bool)]
primeTestPairs = zip [0..] primeTest
-- take 5 primeTestPairs => [(0,False),(1,False),(2,True),(3,True),(4,False)]

-- We are assuming that one prime twin is a pair of numbers i.e. (3,5), (5,7)
primeTwins :: Int -> Int
primeTwins n = length $ take n [x| x <- take n allPrimes, prime (x+2), (elem (x+2) (take n allPrimes))]

-- primeTwins 20 => 7
-- primeTwins 2000 => 302


-- Question 5

main :: IO ()
main = do 
    argsNames <- getArgs
    phonetic argsNames

phonetic :: [String] -> IO ()
phonetic names = do 
    xs <- Prelude.readFile "surnames.txt"
    let 
        fileNames = lines xs
        args2 = map (\x -> x:(filter (areEqual x)) fileNames) names

    putStrLn $ showAll args2

showOneLine :: [String] -> String
showOneLine (x:xs) = x ++ ": " ++ foldr (\x y -> (filter (/= '\r') x) ++ " " ++ y) "" xs

showAll :: [[String]] -> String
showAll xs = foldr (\x y -> showOneLine x ++ "\n" ++ y) "" xs

areEqual :: String -> String -> Bool
areEqual a b = convert a == convert b

convert :: String -> String
convert = removeDouble.stringCon.removeNotFirst.removeCase.removeNonAlpha 

removeNonAlpha :: String -> String 
removeNonAlpha = filter isAlpha

removeCase :: String -> String
removeCase = map toLower

removeNotFirst :: String -> String
removeNotFirst (x:xs) = x:(remove xs)

remove :: String -> String
remove [] = []
remove (x:xs)
    | elem x ['a','e','i','h','o','u','w','y'] = remove xs
    | otherwise = x:remove xs

stringCon :: String -> String
stringCon = map charCon

charCon :: Char -> Char
charCon x 
    | elem x ['a','e','i','o','u'] = 'a'
    | elem x ['c','j','k','q','s','x','y','z'] = 'c'
    | elem x ['b','f','p','v','w'] = 'b'
    | elem x ['d','t'] = 'd'
    | elem x ['m','n'] = 'm'
    | otherwise = x

removeDouble :: String -> String
removeDouble [] = []
removeDouble (x:[]) = [x]
removeDouble (x:y:xs)
    | x == y = removeDouble (y:xs)
    | otherwise = x:removeDouble (y:xs)

