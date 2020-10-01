
/**
 * A class that implements a queue.  It is your job to complete this class.  Your queue
 * will use a linked list constructed by QueueElements.  However, your queue must be general and allow
 * setting of any type of Object.  Also you cannot use ArrayLists or arrays (you will get zero).
 *
 * @author you
 */


import java.util.NoSuchElementException;

/**
 * Queue can hold multiple elements of the given type.
 * You can enqueue elements at the end and dequeue
 * elements from the front. You can also access the
 * first element and check is queue is empty.
 * @param <T> Types of objects held by the queue
 */
public class Queue<T> {

    QueueElement<T> peek;
    QueueElement<T> tail;

    /**
     * Constructs an empty Queue.
     */
    public Queue() {
        peek = null;
        tail = null;
    }

    /**
     * Returns true if the queue is empty
     */
    public boolean isEmpty() {
        return ((peek == null) && (tail == null));
    }


    /**
     * Returns the element at the head of the queue
     */
    public T peek() throws NoSuchElementException {
        if(peek == null){
            throw new NoSuchElementException();
        }
        return peek.getElement();
    }

    /**
     * Removes the front element of the queue
     */
    public void dequeue() throws NoSuchElementException {
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        if (peek.equals(tail)) {
            tail = null;
        }
        if (peek.getNext() != null) {
            peek = peek.getNext();
        } else if (peek.getNext() == null) {
            peek = null;
        }
    }

    /**
     * Puts an element on the back of the queue.
     */
    public void enqueue(T element) {
        if (isEmpty()) {
            peek = new QueueElement<T>(element, null);
        } else if (tail == null) {
            tail = new QueueElement<T>(element, null);
            peek.setNext(tail);
        } else if (!isEmpty()) {
            QueueElement<T> temp = new QueueElement<T>(element, null);
            tail.setNext(temp);
            tail = temp;
        }
    }

    /**
     * Method to print the full contents of the queue in order from head to tail.
     */
    public void print() {
        QueueElement<T> temp = peek;
        while (temp != null) {
            System.out.println(temp.getElement());
            temp = temp.getNext();
        }
        if(isEmpty()){
            System.out.println("The queue is empty. ");
        }
    }
}
