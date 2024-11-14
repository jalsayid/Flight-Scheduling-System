//**************************  SLL.java  *********************************
//           a generic singly linked list class 

public class SLL<T> {

    class SLLNode<T> {
       T info;
       SLLNode<T> next;
      public SLLNode() {
         this(null,null);
      }
      public SLLNode(T el) {
        this(el,null);
      }
      public SLLNode(T el, SLLNode<T> ptr) {
         info = el; next = ptr;
      }
    }

    protected SLLNode<T> head, tail;
    public SLL() {
        head = tail = null;
    }
    public boolean isEmpty() {
        return head == null;
    }
    public void addToHead(T el) {
        head = new SLLNode<T>(el,head);
        if (tail == null)
            tail = head;
    }
    public void addToTail(T el) {
        if (!isEmpty()) {
            tail.next = new SLLNode<T>(el);
            tail = tail.next;
        }
        else head = tail = new SLLNode<T>(el);
    }
    public T deleteFromHead() { // delete the head and return its info; 
        if (isEmpty()) 
             return null;
        T el = head.info;
        if (head == tail)       // if only one node on the list;
             head = tail = null;
        else head = head.next;
        return el;
    }
    public T deleteFromTail() { // delete the tail and return its info;
        if (isEmpty()) 
             return null;
        T el = tail.info;
        if (head == tail)       // if only one node in the list;
             head = tail = null;
        else {                  // if more than one node in the list,
             SLLNode<T> tmp;    // find the predecessor of tail;
             for (tmp = head; tmp.next != tail; tmp = tmp.next);
             tail = tmp;        // the predecessor of tail becomes tail;
             tail.next = null;
        }
        return el;
    }
    public void delete(T el) {  // delete the node with an element el;
        if (!isEmpty())
            if (head == tail && el.equals(head.info)) // if only one
                 head = tail = null;       // node on the list;
            else if (el.equals(head.info)) // if more than one node on the list;
                 head = head.next;    // and el is in the head node;
            else {                    // if more than one node in the list
                 SLLNode<T> pred, tmp;// and el is in a nonhead node;
                 for (pred = head, tmp = head.next;  
                      tmp != null && !tmp.info.equals(el); 
                      pred = pred.next, tmp = tmp.next);
                 if (tmp != null) {   // if el was found;
                     pred.next = tmp.next;
                     if (tmp == tail) // if el is in the last node;
                        tail = pred;
                 }
            }
    }
    
    @Override
    public String toString() {
       if(head == null)
          return "[ ]";
       String str = "[ ";   
       SLLNode<T> tmp = head;
       while(tmp != null){
         str += tmp.info + " ";
         tmp = tmp.next;
       }
       return str+"]";             
    }
    
    public boolean contains(T el) {
        if(head == null)
            return false;
        SLLNode<T> tmp = head;
        while(tmp != null){
           if(tmp.info.equals(el))
              return true;
           tmp = tmp.next;
        }
        return false;
    }
    
    public int size(){
        if(head == null)
          return 0;
          
        int count = 0;
        SLLNode<T> p = head;
        while(p != null) {
           count++;
           p = p.next;
        }
           
        return count;
    }
    
    //  Please write the methods of Task02 here:
    public void insertBefore(int index, T newElem) {
        try {
            // Check if the list is empty
            if(isEmpty())
                throw new IllegalArgumentException("The list is empty.");
            // Check if the index is valid
            if(index < 0 || index >= size())
                throw new IndexOutOfBoundsException("The index is not valid.");
            // Create a new node with the new element
            SLLNode<T> newNode = new SLLNode<T>(newElem);
            SLLNode<T> currentNode = head;
            // Handle the case where the index is 0
            if(index == 0) {
                newNode.next = head;
                head = newNode;
            }
            else {
                // Traverse the list to find the node at index - 1
                for(int i = 0; i < index - 1; i++) {
                    currentNode = currentNode.next;
                }
                // Insert the new node before the node at index
                newNode.next = currentNode.next;
                currentNode.next = newNode;
            }
        } 
        catch (Exception e) {
            // Print the exception message
            System.out.println(e.getMessage());
        }
    }
    public T delete(int index) {
        T deletedValue = null;
        try {
            // Check if the list is empty
            if (isEmpty())
                throw new IllegalArgumentException("The list is empty.");
            // Check if the index is valid
            if (index < 0 || index >= size())
                throw new IndexOutOfBoundsException("The index is not valid.");
            // Handle the case where the index is 0
            if (index == 0) {
                deletedValue = head.info;
                if (head == tail) { // if only one node on the list
                    head = tail = null;
                } 
                else {
                    head = head.next;
                }
            } 
            else {
                SLLNode<T> currentNode = head;
                // Traverse the list to find the node at index - 1
                for (int i = 0; i < index - 1; i++) {
                    currentNode = currentNode.next;
                }
                // Check if the node to delete is the tail
                if (currentNode.next == tail) {
                    deletedValue = tail.info;
                    currentNode.next = null;
                    tail = currentNode;
                } 
                else {
                    deletedValue = currentNode.next.info;
                    currentNode.next = currentNode.next.next;
                }
            }
        } 
        catch (Exception e) {
            // Print the exception message
            System.out.println(e.getMessage());
        }
        return deletedValue;
    }
    public void insertAfterSecondOccurrence(T targetElement, T newElement) {
        try {
            // Check if the list is empty
            if (head == null) {
                throw new IllegalArgumentException("The list is empty.");
            }
            SLLNode<T> current = head;
            int count = 0;
            SLLNode<T> secondOccurrence = null;
            // Traverse the list to find the second occurrence of the target element
            while (current != null) {
                if (current.info.equals(targetElement)) {
                    count++;
                    if (count == 2) {
                        secondOccurrence = current;
                        break;
                    }
                }
                current = current.next;
            }
            // Check if the second occurrence was found
            if (secondOccurrence == null) {
                throw new IllegalArgumentException("No second occurrence of " + targetElement + " found.");
            }
            // Create a new node with the new element and insert it after the second occurrence
            SLLNode<T> newNode = new SLLNode<T>(newElement);
            newNode.next = secondOccurrence.next;
            secondOccurrence.next = newNode;
        } 
        catch (IllegalArgumentException e) {
            // Print the exception message
            System.out.println(e.getMessage());
        }
    }
    public T[] toArray(T[] list) {
        // Check if the provided array is smaller than the size of the linked list
        if (list.length < size()) {
            // Create a new array of the required size using type casting
            list = (T[]) new Object[size()];
        }
        
        // Initialize a currentNode variable with the head of the linked list
        SLLNode<T> currentNode = head;
        // Initialize an index variable to keep track of the array index
        int index = 0;
        
        // Iterate over the linked list
        while (currentNode != null) {
            // Assign the current node's info to the corresponding index in the array
            list[index] = currentNode.info;
            // Move to the next node in the linked list
            currentNode = currentNode.next;
            // Increment the index
            index++;
        }
        
        // If there are remaining indices in the array after iterating over the linked list,
        // set them to null to indicate the end of the valid elements
        if (index < list.length) {
            list[index] = null;
        }
        
        // Return the modified array
        return list;
    }
    
}