import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.NoSuchElementException;


public class AVLTree<K extends Comparable<K>, T>{

//first we start with creat node class amd methods
        class AVLNode<K extends Comparable<K>, T> {
                public K key;
                public T data;
                AVLNode<K,T> parent; 
                AVLNode<K,T> left; //left child
                AVLNode<K,T> right; //right child
                int bf; //balance factor 


                    public AVLNode() {
                            this.key = null;  
                            this.data = null;
                            this.parent = this.left = this.right = null;
                            this.bf = 0;
                    }

                    public AVLNode(K key, T data) {
                            this.key = key  ;  
                            this.data = data;
                            this.parent = this.left = this.right = null;
                            this.bf = 0;
                    }

                    public AVLNode(K key, T data, AVLNode<K,T> p, AVLNode<K,T> l, AVLNode<K,T> r){
                            this.key = key  ;  
                            this.data = data;
                            left = l;
                            right = r;
                            parent = p;
                            bf =0;
                    }

                    public AVLNode<K,T> getLeft()
                    {
                        return left;
                    }

                    public AVLNode<K,T> getRight()
                    {
                        return right;
                    }

                    public T getData()
                    {
                        return data;
                    }
                    
                    @Override
                    public String toString() {
                        return "AVL Node{" + "key=" + key + ", data =" + data + '}';
                    }
            }

      
        private AVLNode<K,T> root;
        private AVLNode<K,T>  curr;
        private int count;
        
        public AVLTree() {
                root = curr = null;
                count = 0;
        }

        public boolean empty() {
            return root == null;
        }

        public int size() {
            return count;
        }


         //a mrthod to Remove all elements in the map
        public void clear()
        {
            root = curr = null;
            count = 0;
        }

        // a method to Return the key and data of the current element
        public T retrieve()
        {
            T data =null;
            if (curr != null)
                data = curr.data;
            return data;
        }

        // a mrthod to Update the data of current element
        public void update(T e)
        {
            if (curr != null)
                curr.data = e;
        }

        // a method to search for the key in the AVL, returns the data or null
        private T searchTreeHelper(AVLNode<K,T> node, K key) {
                    if (node == null)
                        return null;
                    else if (node.key.compareTo(key) ==0) 
                    {
                        curr = node;
                        return node.data;
                    } 
                    else if (node.key.compareTo(key) >0)
                         return searchTreeHelper(node.left, key);
                    else
                         return searchTreeHelper(node.right, key);
        }
        
        // a method to update the balance factor the node
        private void updateBalance(AVLNode<K,T> node) {
                if (node.bf < -1 || node.bf > 1) {
                        rebalance(node);
                        return;
                }

                if (node.parent != null) {
                        if (node == node.parent.left) {
                                node.parent.bf -= 1;
                        } 

                        if (node == node.parent.right) {
                                node.parent.bf += 1;
                        }

                        if (node.parent.bf != 0) {
                                updateBalance(node.parent);
                        }
                }
        }

        // till we can rebalance the tree
        void rebalance(AVLNode<K,T> node) {
                if (node.bf > 0) {
                        if (node.right.bf < 0) {
                                rightRotate(node.right);
                                leftRotate(node);
                        } else {
                                leftRotate(node);
                        }
                } else if (node.bf < 0) {
                        if (node.left.bf > 0) {
                                leftRotate(node.left);
                                rightRotate(node);
                        } else {
                                rightRotate(node);
                        }
                }
        }

        public boolean find(K key) {
                T data = searchTreeHelper(this.root, key);
                if ( data != null)
                    return true;
                return false;
        }

        // rotate left at node x
        void leftRotate(AVLNode<K,T> x) {
            AVLNode<K,T> y = x.right;
            x.right = y.left;
            if (y.left != null) {
                    y.left.parent = x;
            }
            
            y.parent = x.parent;
            if (x.parent == null) {
                    this.root = y;
            } else if (x == x.parent.left) {
                    x.parent.left = y;
            } else {
                    x.parent.right = y;
            }
            y.left = x;
            x.parent = y;

            // update the balance factor
            x.bf = x.bf - 1 - Math.max(0, y.bf);
            y.bf = y.bf - 1 + Math.min(0, x.bf);
        }

        // rotate right at node x
        void rightRotate(AVLNode<K,T> x) {
                AVLNode<K,T> y = x.left;
                x.left = y.right;
                if (y.right != null) {
                        y.right.parent = x;
                }
                y.parent = x.parent;
                if (x.parent == null) {
                        this.root = y;
                } else if (x == x.parent.right) {
                        x.parent.right = y;
                } else {
                        x.parent.left = y;
                }
                y.right = x;
                x.parent = y;

                // update the balance factor
                x.bf = x.bf + 1 - Math.min(0, y.bf);
                y.bf = y.bf + 1 + Math.max(0, x.bf);
        }

        
        // a method to insert 
        public boolean insert(K key, T data) {
            AVLNode<K,T> node = new AVLNode<K,T>(key, data);

            AVLNode<K,T> p = null;
            AVLNode<K,T> current = this.root;

            while (current != null) {
                    p = current;
                    if (node.key.compareTo(current.key) ==0) {
                            return false;
                    }else if (node.key.compareTo(current.key) <0 ) {
                            current = current.left;
                    } else {
                            current = current.right;
                    }
            }
            // p  is parent of current
            node.parent = p;
            if (p == null) {
                    root = node;
                    curr = node;
            } else if (node.key.compareTo(p.key) < 0 ) {
                    p.left = node;
            } else {
                    p.right = node;
            }
            count ++;

            //  re-balance the node 
            updateBalance(node);
            return true;        
        }
    // a mrthod to remove    
    public boolean removeKey(K key) {
        K k1 = key;
        AVLNode<K,T>  p = root;
        AVLNode<K,T>  q = null; // Parent of p

        while (p != null) 
        {
                if (k1.compareTo(p.key) <0)
                {
                    q =p;
                    p = p.left;
                } 
                else if (k1.compareTo(p.key) >0)
                {    
                    q = p;
                    p = p.right;
                }
                else 
                { // Found the key
                    // Check the three cases
                    if ((p.left != null) && (p.right != null)) 
                    { 
                        // Case 3: two children
                        // Search for the min in the right subtree
                        AVLNode<K,T> min = p.right;
                        q = p;
                        while (min.left != null) 
                        {
                            q = min;
                            min = min.left;
                        }
                        p.key = min.key;
                        p.data = min.data;
                        k1 = min.key;
                        p = min;
                        // Now fall back to either case 1 or 2
                    }
                    // The subtree rooted at p will change here
                    if (p.left != null) 
                    { 
                        // One child
                        p = p.left;
                    } 
                    else 
                    { 
                        // One or no children
                        p = p.right;
                    }
                    if (q == null)
                    { 
                        // No parent for p, root must change
                        root = p;
                        this.updateBalance(p);
                    } 
                    else 
                    {
                        if (k1.compareTo(q.key) <0)
                            q.left = p;
                        else 
                            q.right = p;
                        this.updateBalance(q);
                    }
                    count--;
                    curr = root;
                    return true;    
            } 
        } // end while (p != null) 
        return false;
    }


    // return string all data of the tree inorder traversal
   
    @Override
public String toString() {
    String str = inOrdersTraversal(root);
    
    // Safely remove the last "; " if it exists
    if (str.length() >= 2 && str.endsWith("; ")) {
        str = str.substring(0, str.length() - 2);
    }
    
    return "{" + str + "}";
}
  
    // a mthodd to print just keys in order traversal
    public void printKeys()
    {
        private_printKeys(root);
    }
    
    private String inOrdersTraversal(AVLNode<K, T> node) {
    if (node == null)
        return "";
    
    String left = inOrdersTraversal(node.left);
    String current = node.data.toString();
    String right = inOrdersTraversal(node.right);
    
    // Build the string without trailing semicolon
    StringBuilder result = new StringBuilder();
    if (!left.isEmpty()) {
        result.append(left).append("; ");
    }
    result.append(current);
    if (!right.isEmpty()) {
        result.append("; ").append(right);
    }
    
    return result.toString();
}
    private void private_printKeys(AVLNode<K, T>  node)
    {
        if (node == null)
            return ;
        private_printKeys(node.left);
        System.out.println(node.key);
        private_printKeys(node.right);
        
    }


    // a methid to print keys in order traversal
    public void printKeys_Data()
    {
        private_printKeys_Data(root);
    }
    
    private void private_printKeys_Data(AVLNode<K, T>  node)
    {
        if (node == null)
            return ;
        private_printKeys_Data(node.left);
        System.out.print(node.key);
        System.out.println(node.data);
        System.out.println("");
        private_printKeys_Data(node.right);
        
    }


    // a method to print just keys in order traversal
    public void printData()
    {
        private_printData(root);
    }
    
    private void private_printData(AVLNode<K, T>  node)
    {
        if (node == null)
            return ;
        private_printData(node.left);
        System.out.println(node.data);
        System.out.println("");
        private_printData(node.right);
        
    }
    

    // a method to return list of all data of the tree inorder traversal
    public LinkedList<T>  inOrdertraverseData() {
        LinkedList<T> data = new LinkedList<T>();
        private_inOrdertraverseData( root , data);
        return data;
    }

    private void  private_inOrdertraverseData(AVLNode<K, T>  node, LinkedList<T> data)
    {
        if (node == null)
            return ;
        private_inOrdertraverseData(node.left, data );
        data.insert(node.data);
        private_inOrdertraverseData(node.right, data);
    }
  

    // to search for interval between two keys k1 , and k2
    public LinkedList <T> intervalSearch(K k1, K k2)
    {
        LinkedList <T> q = new LinkedList <T>();
        if (root != null)
           rec_intervalSearch (k1 , k2, root , q);
        return q;
    }
    
    private void rec_intervalSearch (K k1, K k2, AVLNode <K, T> p , LinkedList <T> q)
    {
        if (p == null)
            return;
        else
        {
                rec_intervalSearch (k1, k2 , p.left , q);
                if (( p.key.compareTo(k1) >= 0 ) && (p.key.compareTo(k2) <= 0 ))
                    q.insert(p.data);
                rec_intervalSearch (k1, k2 , p.right , q);
            
         }
    }   
    

    // to search for interval orders between two dates
    public AVLTree <Date,T> intervalSearchDate(LocalDate k1, LocalDate k2)
    {
        AVLTree <Date,T> q = new AVLTree <Date,T>();
        if (root != null)
            if ( root.data instanceof Order )
               rec_intervalSearchDate (k1 , k2, root , q);
        return q;
    }
    
    private void rec_intervalSearchDate (LocalDate Ldate1, LocalDate Ldate2, AVLNode <K, T> p , AVLTree <Date,T> q)
    {
        if (p == null)
            return;
        else
        {
                rec_intervalSearchDate (Ldate1, Ldate2 , p.left , q);
                if ( p.data instanceof Order )
                    if (((Order) p.data).getDate().isAfter(Ldate1) && ((Order) p.data).getDate().isBefore(Ldate2))
                    {
                        Date date = new Date (((Order) p.data).getDate().getDayOfMonth()
                                        , ((Order) p.data).getDate().getMonthValue()
                                        , ((Order) p.data).getDate().getYear() );
                        q.insert(date , p.data);
                    }
                        
                rec_intervalSearchDate (Ldate1, Ldate2 , p.right , q);
            
         }
    }   

        
    // a method to find all products out of stock in order traversal
    public void inOrdertraverseOutStock() {
        System.out.println("Products out of stock");
        System.out.println("====================");
        if (( root != null) && ( root.data instanceof Product))
            private_inOrdertraverseOutStock( root);
    }

    private void  private_inOrdertraverseOutStock(AVLNode<K, T>  node)
    {
        if (node == null)
            return ;
        private_inOrdertraverseOutStock(node.left);
        if (node.data instanceof Product);
            if ( ((Product)node.data).getStock() == 0)
                System.out.println(node.data);
        private_inOrdertraverseOutStock(node.right);
    }

        // a method to search for interval between two data price k1 , and k2
    public LinkedList <T> intervalSearchprice(double k1, double k2)
    {
        LinkedList <T> q = new LinkedList <T>();
        if (( root != null) && ( root.data instanceof Product))
           rec_intervalSearchprice (k1 , k2, root , q);
        return q;
    }
    
    private void rec_intervalSearchprice (double k1, double k2, AVLNode <K, T> p , LinkedList <T> q)
    {
        if (p == null)
            return;
        else
        {
                rec_intervalSearchprice (k1, k2 , p.left , q);
                if (p.data instanceof Product) 
                if ((( (Product) p.data).getPrice() >= k1 ) && ((( Product) p.data).getPrice() <=k2))
                    q.insert(p.data);
                rec_intervalSearchprice (k1, k2 , p.right , q);
            
         }
    }   
    

    public void LoadToFile ( String fileName)
    {
        
        try {
                FileWriter myWriter = new FileWriter(fileName);
                private_LoadToFile ( myWriter, root);
                myWriter.close();

        } catch(IOException ex){
                     System.out.println(ex.getMessage());
        } 

    }
    private void private_LoadToFile ( FileWriter myWriter,  AVLNode <K, T>  node) throws IOException
    {
        if ( node == null)
            return;
        private_LoadToFile ( myWriter,  node.left);
        if (node.data instanceof Customer)
            myWriter.write(((Customer) node.data).getDataToFile() +"\n");
        if (node.data instanceof Order)
            myWriter.write(((Order) node.data).getDataToFile() +"\n");
        if (node.data instanceof Product)
            myWriter.write(((Product) node.data).getDataToFile() +"\n");
        private_LoadToFile ( myWriter,  node.right);
        
    }    
}
