import java.util.Iterator;
/************************  BST.java  **************************
 *                 generic binary search tree
 */

public class BST<T extends Comparable<? super T>> implements Iterable<T> {
    protected BSTNode<T> root; 
        
    public BST() {
    	root = null;
    }
    
    //ADDED METHOD FOR AVL TREES
    public BST(BSTNode<T> node) {
    	root = node;
    }

    public void clear() {
        root = null;
    }
    
    public boolean isEmpty() {
        return root == null;
    }
    
    public void insert(T el) {
        BSTNode<T> p = root, prev = null;
        while (p != null) {  // find a place for inserting new node;
            prev = p;
            if (el.compareTo(p.el) < 0)
                 p = p.left;
            else p = p.right;
        }
        if (root == null)    // tree is empty;
             root = new BSTNode<T>(el);
        else if (el.compareTo(prev.el) < 0)
             prev.left  = new BSTNode<T>(el);
        else prev.right = new BSTNode<T>(el);
    }
    
    public void recInsert(T el) {
        root = recInsert(root,el);
    }
    
    protected BSTNode<T> recInsert(BSTNode<T> p, T el) {
        if (p == null)
             p = new BSTNode<T>(el);
        else if (el.compareTo(p.el) < 0)
             p.left  = recInsert(p.left,el);
        else p.right = recInsert(p.right,el);
        return p;
    }
    
    public boolean isInTree(T el) {
        return search(el) != null;
    }
    
    protected T search(T el) {
        BSTNode<T> p = root;
        while (p != null)
            if (el.equals(p.el))
                 return p.el;
            else if (el.compareTo(p.el) < 0)
                 p = p.left;
            else p = p.right;
        return null;
    }
    
    public void preorder() {
        preorder(root);
    }
    
    public void inorder() {
        inorder(root);
    }
    
    public void postorder() {
        postorder(root);
    }
    
    protected void visit(BSTNode<T> p) {
        System.out.print(p.el + " ");
    }
    
    protected void inorder(BSTNode<T> p) {
        if (p != null) {
             inorder(p.left);
             visit(p);
             inorder(p.right);
        }
    }
    
    protected void preorder(BSTNode<T> p) {
        if (p != null) {
             visit(p);
             preorder(p.left);
             preorder(p.right);
        }
    }
    
    protected void postorder(BSTNode<T> p) {
        if (p != null) {
             postorder(p.left);
             postorder(p.right);
             visit(p);
        }
    }
    
    public void deleteByCopying(T el) {
        BSTNode<T> node, p = root, prev = null;
        while (p != null && !p.el.equals(el)) {  // find the node p
             prev = p;                           // with element el;
             if (el.compareTo(p.el) < 0)
                  p = p.left;
             else p = p.right;
        }
        node = p;
        if (p != null && p.el.equals(el)) {
             if (node.right == null)             // node has no right child;
                  node = node.left;
             else if (node.left == null)         // no left child for node;
                  node = node.right;
             else {
                  BSTNode<T> tmp = node.left;    // node has both children;
                  BSTNode<T> previous = node;    // 1.
                  while (tmp.right != null) {    // 2. find the rightmost
                      previous = tmp;            //    position in the
                      tmp = tmp.right;           //    left subtree of node;
                  }
                  node.el = tmp.el;              // 3. overwrite the reference
                                                 //    to the element being deleted;
                  if (previous == node)          // if node's left child's
                      previous.left  = tmp.left; // right subtree is null;
                 else previous.right = tmp.left; // 4.
             }
             if (p == root)
                  root = node;
             else if (prev.left == p)
                  prev.left = node;
             else prev.right = node;
        }
        else if (root != null)
             System.out.println("el " + el + " is not in the tree");
        else System.out.println("the tree is empty");
    }
    
    public void deleteByMerging(T el) {
        BSTNode<T> tmp, node, p = root, prev = null;
        while (p != null && !p.el.equals(el)) {  // find the node p
             prev = p;                           // with element el;
             if (el.compareTo(p.el) < 0)
                  p = p.right;
             else p = p.left;
        }
        node = p;
        if (p != null && p.el.equals(el)) {
             if (node.right == null) // node has no right child: its left
                  node = node.left;  // child (if any) is attached to its parent;
             else if (node.left == null) // node has no left child: its right
                  node = node.right; // child is attached to its parent;
             else {                  // be ready for merging subtrees;
                  tmp = node.left;   // 1. move left
                  while (tmp.right != null) // 2. and then right as far as
                      tmp = tmp.right;      //    possible;
                  tmp.right =        // 3. establish the link between
                      node.right;    //    the rightmost node of the left
                                     //    subtree and the right subtree;
                  node = node.left;  // 4.
             }
             if (p == root)
                  root = node;
             else if (prev.left == p)
                  prev.left = node;
             else prev.right = node; // 5.
        }
        else if (root != null)
             System.out.println("el " + el + " is not in the tree");
        else System.out.println("the tree is empty");
    }
    
    public void iterativePreorder() {
        BSTNode<T> p = root;
        Stack<BSTNode<T>> travStack = new Stack<BSTNode<T>>();
        if (p != null) {
             travStack.push(p);
             while (!travStack.isEmpty()) {
                 p = travStack.pop();
                 visit(p);
                 if (p.right != null)
                      travStack.push(p.right);
                 if (p.left  != null)        // left child pushed after right
                      travStack.push(p.left);// to be on the top of the stack;
             }
        }
    }
    public void iterativeInorder() {
        BSTNode<T> p = root;
        Stack<BSTNode<T>> travStack = new Stack<BSTNode<T>>();
        while (p != null) {
            while(p != null) {               // stack the right child (if any)
                 if (p.right != null)        // and the node itself when going
                    travStack.push(p.right); // to the left;
                 travStack.push(p);
                 p = p.left;
            }
            p = travStack.pop();             // pop a node with no left child
            while (!travStack.isEmpty() && p.right == null) { // visit it and all
                 visit(p);                   // nodes with no right child;
                 p =  travStack.pop();
            }
            visit(p);                        // visit also the first node with
            if (!travStack.isEmpty())        // a right child (if any);
                 p = travStack.pop();
            else p = null;
        }
    }
    
    public void iterativePostorder2() {
        BSTNode<T> p = root;
        Stack<BSTNode<T>> travStack = new Stack<BSTNode<T>>(),
                          output = new Stack<BSTNode<T>>();
        if (p != null) {        // left-to-right postorder = right-to-left preorder;
             travStack.push(p);
             while (!travStack.isEmpty()) {
                 p = travStack.pop();
                 output.push(p);
                 if (p.left != null)
                      travStack.push(p.left);
                 if (p.right != null)
                      travStack.push(p.right);
             }
             while (!output.isEmpty()) {
                 p = output.pop();
                 visit(p);
             }
        }
    }
    
    public void iterativePostorder() {
        BSTNode<T> p = root, q = root;
        Stack<BSTNode<T>> travStack = new Stack<BSTNode<T>>();
        while (p != null) {
            for ( ; p.left != null; p = p.left)
                travStack.push(p);
            while (p != null && (p.right == null || p.right == q)) {
                visit(p);
                q = p;
                if (travStack.isEmpty())
                     return;
                p = travStack.pop();
            }
            travStack.push(p);
            p = p.right;
        }
    }
    
    public void breadthFirst() {
        BSTNode<T> p = root;
        Queue<BSTNode<T>> queue = new Queue<BSTNode<T>>();
        if (p != null) {
             queue.enqueue(p);
             while (!queue.isEmpty()) {
                 p = queue.dequeue();
                 visit(p);
                 if (p.left != null)
                      queue.enqueue(p.left);
                 if (p.right != null)
                      queue.enqueue(p.right);
             }
        }
    }
    
    public void MorrisInorder() {
        BSTNode<T> p = root, tmp;
        while (p != null)
            if (p.left == null) {
                 visit(p);
                 p = p.right;
            }
            else {
                 tmp = p.left;
                 while (tmp.right != null && // go to the rightmost node of
                        tmp.right != p)  // the left subtree or
                      tmp = tmp.right;   // to the temporary parent of p;
                 if (tmp.right == null) {// if 'true' rightmost node was
                      tmp.right = p;     // reached, make it a temporary
                      p = p.left;        // parent of the current root,
                 }
                 else {                  // else a temporary parent has been
                      visit(p);          // found; visit node p and then cut
                      tmp.right = null;  // the right pointer of the current
                      p = p.right;       // parent, whereby it ceases to be
                 }                       // a parent;
            }
    }
    
    public void MorrisPreorder() {
        BSTNode<T> p = root, tmp;
        while (p != null) {
            if (p.left == null) {
                 visit(p);
                 p = p.right;
            }
            else {
                 tmp = p.left;
                 while (tmp.right != null && // go to the rightmost node of
                        tmp.right != p)  // the left subtree or
                      tmp = tmp.right;   // to the temporary parent of p;
                 if (tmp.right == null) {// if 'true' rightmost node was
                      visit(p);          // reached, visit the root and
                      tmp.right = p;     // make the rightmost node a temporary
                      p = p.left;        // parent of the current root,
                 }
                 else {                  // else a temporary parent has been
                      tmp.right = null;  // found; cut the right pointer of
                      p = p.right;       // the current parent, whereby it ceases
                 }                       // to be a parent;
            }
        }
    }
    
    public void MorrisPostorder() {
        BSTNode<T> p = new BSTNode<T>(), tmp, q, r, s;
        p.left = root;
        while (p != null)
            if (p.left == null)
                 p = p.right;
            else {
                 tmp = p.left;
                 while (tmp.right != null &&  // go to the rightmost node of
                        tmp.right != p)  // the left subtree or
                      tmp = tmp.right;   // to the temporary parent of p;
                 if (tmp.right == null) {// if 'true' rightmost node was
                      tmp.right = p;     // reached, make it a temporary
                      p = p.left;        // parent of the current root,
                 }
                 else {           // else a temporary parent has been found;
                      // process nodes between p.left (included) and p (excluded)
                      // extended to the right in modified tree in reverse order;
                      // the first loop descends this chain of nodes and reverses
                      // right pointers; the second loop goes back, visits nodes,
                      // and reverses right pointers again to restore the pointers
                      // to their original setting;
                      for (q = p.left, r = q.right, s = r.right;
                           r != p; q = r, r = s, s = s.right)
                          r.right = q;
                      for (s = q.right; q != p.left;
                           q.right = r, r = q, q = s, s = s.right)
                          visit(q);
                      visit(p.left);     // visit node p.left and then cut
                      tmp.right = null;  // the right pointer of the current
                      p = p.right;       // parent, whereby it ceases to be
                 }                       // a parent;
            }
    }
    
    public void balance(T data[], int first, int last) {
        if (first <= last) {
            int middle = (first + last)/2;
            insert(data[middle]);
            balance(data,first,middle-1);
            balance(data,middle+1,last);
        }
    }
    
    public void balance(T data[]) {
        balance(data,0,data.length-1);
    }
    
    public Iterator<T> iterator() {
     return new BSTIterator();
 }
 
 // Inner class that implements the Iterator interface
 private class BSTIterator implements Iterator<T> {
     private Stack<BSTNode<T>> stack;
 
     // Constructor for the iterator
     public BSTIterator() {
         stack = new Stack<>();
         pushLeftNodes(root); // Pushes the left nodes of the tree onto the stack
     }
 
     // Helper method to push the left nodes of a subtree onto the stack
     private void pushLeftNodes(BSTNode<T> node) {
         while (node != null) {
             stack.push(node); // Push the current node onto the stack
             node = node.left; // Move to the left child of the current node
         }
     }
 
     // Check if there is a next element in the iteration
     public boolean hasNext() {
         return !stack.isEmpty(); // Returns true if the stack is not empty
     }
 
     // Get the next element in the iteration
     public T next() {
         BSTNode<T> node = stack.pop(); // Pop the top node from the stack
         pushLeftNodes(node.right); // Push the left nodes of the right child onto the stack
         return node.el; // Return the element of the popped node
     }
 }
}