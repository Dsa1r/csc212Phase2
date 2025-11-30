import java.io.FileWriter;
import java.io.IOException;

public class LinkedList<T>{

        // for class node
            class Node<T> {
            public T data;
            public Node<T> next;
            public Node () {
                data = null;
                next = null;
            }
            public Node (T val) {
                data = val;
                next = null;
            }
                
            // Setters and Getters if needed

            public T getData() {
                return data;
            }

            public void setData(T data) {
                this.data = data;
            }

            public Node<T> getNext() {
                return next;
            }

            public void setNext(Node<T> next) {
                this.next = next;
            }

        }
    
    // for class Linked List
    private Node<T> head;
    private Node<T> current;
    int size;
    
    public LinkedList () {
        head = current = null;
        size = 0 ;
    }
    public boolean empty () {
        return head == null;
    }
    public int size ()
    {
        return size;
    }
    
    public boolean last () {
        return current.next == null;
    }
    public boolean full () {
            return false;
    }
    public void findFirst () {
            current = head;
    }
    public void findNext () {
            current = current.next;
    }
    
    public void findLast()
    {
        current = head;
        if (current != null)
        {
            while ( current.next != null)
                current = current.next;
        }
    }
    // a mthod to to retrieve
    public T retrieve () {
            return current.data;
    }
    
    public void update (T val) {
            current.data = val;
    }
    
  // a method to insert  
    public void insert (T val) {
            Node<T> tmp;
            if (empty()) {
                    current = head  = new Node<T> (val);
            }
            else {
                    
                    tmp = current.next;
                    current.next = new Node<T> (val);
                    current = current.next;
                    current.next =tmp;
            }
            size++ ;
    }

    // a method to remove
    public void remove () {
            if (current == head) {
                    head = head.next;
            }
            else {
                    Node<T> tmp = head;

                    while (tmp.next != current)
                            tmp = tmp.next;

                    tmp.next = current.next;
            }

            if (current.next == null)
                    current = head;
            else
                    current = current.next;
            size --;
    }

    
    public void print()
    {
        if ( head == null)
            System.out.println("Empty data");
        else
        {
            Node<T> tmp = head;
            while ( tmp != null)
            {
                System.out.print(tmp.data + "  ");
                tmp = tmp.next;
            }
            
        }
        System.out.println("");
    }

    
    public void LoadToFile ( String fileName)
    {
        
        try {
                FileWriter myWriter = new FileWriter(fileName);
                
                Node<T> tmp = head;
     
                while (tmp != null)
                {
                    if (tmp.data instanceof Review)
                        myWriter.write(((Review) tmp.data).getDataToFile() +"\n");
                    
                    tmp = tmp.next;
                }
                
                myWriter.close();

        } catch(IOException ex){
                     System.out.println(ex.getMessage());
        } 

    }
}
