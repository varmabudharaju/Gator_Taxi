import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Collections;

public class gatorTaxi {
    static class Ride {
        private int rideNumber;
        private int rideCost;
        private int tripDuration;
    
        public Ride(int rideNumber, int rideCost, int tripDuration) {
            this.rideNumber = rideNumber;
            this.rideCost = rideCost;
            this.tripDuration = tripDuration;
        }
    
        public int getRideNumber() {
            return rideNumber;
        }
    
        public void setRideNumber(int rideNumber) {
            this.rideNumber = rideNumber;
        }
    
        public int getRideCost() {
            return rideCost;
        }
    
        public void setRideCost(int rideCost) {
            this.rideCost = rideCost;
        }
    
        public int getTripDuration() {
            return tripDuration;
        }
    
        public void setTripDuration(int tripDuration) {
            this.tripDuration = tripDuration;
        }
        public String toString() {
            return "(" + rideNumber + "," + rideCost + "," + tripDuration + ")";
        }
    }   
    static class MinHeap {
    
    private ArrayList<Ride> rides;
    private int maxSize=100;
    public MinHeap() {
        rides = new ArrayList<>();   
    }

    public boolean insert(Ride ride) {
        if (rides.size() >= maxSize) {
            return false;
        }
    
        rides.add(ride);
        int index = rides.size() - 1;
        heapifyUp(index);
    
        return true;
    }
    
    public void updateRide(Ride updatedRide) {
        delete(updatedRide.getRideNumber());
        insert(updatedRide);
    }
    
    public Ride extractMin(RedBlackTree redBlackTree)  {
        if (rides.isEmpty()) {
            return null;
        }
        Ride min = rides.get(0);
        Collections.swap(rides, 0, rides.size() - 1);
        rides.remove(rides.size() - 1);
        heapifyDown(0);
        redBlackTree.delete(min.getRideNumber());
        return min;
    }
    public void delete(int rideNumber) {
        int index = -1;
        for (int i = 0; i < rides.size(); i++) {
            if (rides.get(i).getRideNumber() == rideNumber) {
                index = i;
                break;
            }
        }
    
        if (index != -1) {
            // Swap the ride to delete with the last ride in the heap
            Collections.swap(rides, index, rides.size() - 1);
            rides.remove(rides.size() - 1);
    
            // Re-heapify the heap
            if (index < rides.size()) {
                heapifyUp(index);
                heapifyDown(index);
            }
        }
    }
    
    // Implement swim and sink operations to maintain heap order
    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
    
            if (rides.get(parentIndex).getRideCost() < rides.get(index).getRideCost() ||
                    (rides.get(parentIndex).getRideCost() == rides.get(index).getRideCost() &&
                    rides.get(parentIndex).getTripDuration() <= rides.get(index).getTripDuration())) {
                break;
            }
    
            Collections.swap(rides, parentIndex, index);
            index = parentIndex;
        }
    }
    
    
    private void heapifyDown(int index) {
        int size = rides.size();
        while (index < size) {
            int leftChildIndex = 2 * index + 1;
            int rightChildIndex = 2 * index + 2;
    
            if (leftChildIndex >= size) {
                break;
            }
    
            int minChildIndex = leftChildIndex;
            if (rightChildIndex < size &&
                    (rides.get(rightChildIndex).getRideCost() < rides.get(leftChildIndex).getRideCost() ||
                    (rides.get(rightChildIndex).getRideCost() == rides.get(leftChildIndex).getRideCost() &&
                    rides.get(rightChildIndex).getTripDuration() < rides.get(leftChildIndex).getTripDuration()))) {
                minChildIndex = rightChildIndex;
            }
    
            if (rides.get(index).getRideCost() < rides.get(minChildIndex).getRideCost() ||
                    (rides.get(index).getRideCost() == rides.get(minChildIndex).getRideCost() &&
                    rides.get(index).getTripDuration() <= rides.get(minChildIndex).getTripDuration())) {
                break;
            }
    
            Collections.swap(rides, index, minChildIndex);
            index = minChildIndex;
        }
    }
    
    
    private void swim(int index) {
        // Move the element up the heap until it reaches the correct position
    }

    private void sink(int index) {
        // Move the element down the heap until it reaches the correct position
    }
}
enum Color {
    RED, BLACK;
}
public static class RedBlackNode {
    Ride ride;
    RedBlackNode left, right, parent;
    Color color;

    public RedBlackNode(Ride ride) {
        this.ride = ride;
        left = right = parent = null;
        color = Color.RED;
    }
}


static class RedBlackTree {
    private RedBlackNode root;
    private RedBlackNode nil;

    public RedBlackTree() {
        nil = new RedBlackNode(null);
        nil.color = Color.BLACK;
        root = nil;
    }
    public boolean repeat(Ride ride) {
        RedBlackNode y = nil;
        RedBlackNode x = root;
    
        while (x != nil) {
            y = x;
            if (ride.getRideNumber() < x.ride.getRideNumber()) {
                x = x.left;
            } else if (ride.getRideNumber() > x.ride.getRideNumber()) {
                x = x.right;
            } else {
                // Ride number already exists in the tree
                //System.out.println("Duplicate Ride Number");
                return true;
            }
        }
    
        // Ride number not found in the tree
        return false;
    }

    public void insert(Ride ride) {
        RedBlackNode node = new RedBlackNode(ride);
        RedBlackNode y = nil;
        RedBlackNode x = root;
        
        while (x != nil) {
            y = x;
            if (node.ride.getRideNumber() == x.ride.getRideNumber()) {
               // System.out.println("Duplicate Ride Number");
                return;
            } else if (node.ride.getRideNumber() < x.ride.getRideNumber()) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        node.parent = y;
        if (y == nil) {
            root = node;
        } else if (node.ride.getRideNumber() < y.ride.getRideNumber()) {
            y.left = node;
        } else {
            y.right = node;
        }
    
        node.left = nil;
        node.right = nil;
        node.color = Color.RED;
        insertFixup(node);
    }
    

    private void insertFixup(RedBlackNode node) {
        while (node.parent.color == Color.RED) {
            if (node.parent == node.parent.parent.left) {
                RedBlackNode uncle = node.parent.parent.right;
    
                // Case 1: Uncle is red
                if (uncle.color == Color.RED) {
                    node.parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    node = node.parent.parent;
                } else {
                    // Case 2: Uncle is black and the node is the right child
                    if (node == node.parent.right) {
                        node = node.parent;
                        leftRotate(node);
                    }
    
                    // Case 3: Uncle is black and the node is the left child
                    node.parent.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    rightRotate(node.parent.parent);
                }
            } else {
                RedBlackNode uncle = node.parent.parent.left;
    
                // Case 1: Uncle is red
                if (uncle.color == Color.RED) {
                    node.parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    node = node.parent.parent;
                } else {
                    // Case 2: Uncle is black and the node is the left child
                    if (node == node.parent.left) {
                        node = node.parent;
                        rightRotate(node);
                    }
    
                    // Case 3: Uncle is black and the node is the right child
                    node.parent.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    leftRotate(node.parent.parent);
                }
            }
        }
        root.color = Color.BLACK;
    }
    private void leftRotate(RedBlackNode node) {
        RedBlackNode y = node.right;
        node.right = y.left;
        if (y.left != nil) {
            y.left.parent = node;
        }
        y.parent = node.parent;
    
        if (node.parent == nil) {
            root = y;
        } else if (node == node.parent.left) {
            node.parent.left = y;
        } else {
            node.parent.right = y;
        }
        y.left = node;
        node.parent = y;
    }
    
    private void rightRotate(RedBlackNode node) {
        RedBlackNode y = node.left;
        node.left = y.right;
        if (y.right != nil) {
            y.right.parent = node;
        }
        y.parent = node.parent;
    
        if (node.parent == nil) {
            root = y;
        } else if (node == node.parent.right) {
            node.parent.right = y;
        } else {
            node.parent.left = y;
        }
        y.right = node;
        node.parent = y;
    }
    
    public String printRide(int rideNumber) {
        RedBlackNode node = search(root, rideNumber);
        if (node != null && node.ride != null) {
            return node.ride.toString();
        }
        return "(0,0,0)";
    }
    public void updateTrip(int rideNumber, int newTripDuration, MinHeap minHeap) {
        RedBlackNode node = search(root, rideNumber);
        if (node != null && node.ride != null) {
            Ride ride = node.ride;
            int oldTripDuration = ride.getTripDuration();
            int oldRideCost = ride.getRideCost();
            
            if (newTripDuration <= oldTripDuration) {
                // Update the trip duration without modifying rideNumber or rideCost
                ride.setTripDuration(newTripDuration);
                minHeap.updateRide(ride);
            } else if (oldTripDuration < newTripDuration && newTripDuration <= 2 * oldTripDuration) {
                // Cancel the existing ride and create a new ride request with a penalty of 10 on the existing rideCost
                ride.setTripDuration(newTripDuration);
                ride.setRideCost(oldRideCost + 10);
                minHeap.updateRide(ride);
            } else if (newTripDuration > 2 * oldTripDuration) {
                // Remove the ride from the data structure
                delete(ride.getRideNumber());
            }
        }
    }
    
    public String printRidesInRange(int rideNumber1, int rideNumber2) {
        StringBuilder sb = new StringBuilder();
        printRidesInRangeHelper(root, rideNumber1, rideNumber2, sb);
        if (sb.length() == 0) {
            return "(0,0,0)";
        }
        return sb.toString();
    }
    
    private void printRidesInRangeHelper(RedBlackNode node, int rideNumber1, int rideNumber2, StringBuilder sb) {
        if (node != nil) {
            printRidesInRangeHelper(node.left, rideNumber1, rideNumber2, sb);
    
            if (rideNumber1 <= node.ride.getRideNumber() && node.ride.getRideNumber() <= rideNumber2) {
                sb.append("(")
                  .append(node.ride.getRideNumber())
                  .append(",")
                  .append(node.ride.getRideCost())
                  .append(",")
                  .append(node.ride.getTripDuration())
                  .append(")");
            }
            printRidesInRangeHelper(node.right, rideNumber1, rideNumber2, sb);
        }
        
    }
    
    public void delete(int rideNumber) {
        RedBlackNode node = search(rideNumber);
        if (node == null || node == nil) {
            return;
        }
    
        RedBlackNode y = node;
        RedBlackNode x;
        Color yOriginalColor = y.color;
    
        if (node.left == nil) {
            x = node.right;
            transplant(node, node.right);
        } else if (node.right == nil) {
            x = node.left;
            transplant(node, node.left);
        } else {
            y = treeMinimum(node.right);
            yOriginalColor = y.color;
            x = y.right;
    
            if (y.parent == node) {
                x.parent = y;
            } else {
                transplant(y, y.right);
                y.right = node.right;
                y.right.parent = y;
            }
    
            transplant(node, y);
            y.left = node.left;
            y.left.parent = y;
            y.color = node.color;
        }
    
        if (yOriginalColor == Color.BLACK) {
            deleteFixup(x);
        }
    }
    
    public RedBlackNode search(int rideNumber) {
        return search(root, rideNumber);
    }
    private RedBlackNode search(RedBlackNode node, int rideNumber) {
        if (node == null || node == nil) {
            return null;
        }

        if (rideNumber == node.ride.getRideNumber()) {
            return node;
        } else if (rideNumber < node.ride.getRideNumber()) {
            return search(node.left, rideNumber);
        } else {
            return search(node.right, rideNumber);
        }
    }

    
    private RedBlackNode treeMinimum(RedBlackNode node) {
        while (node.left != nil) {
            node = node.left;
        }
        return node;
    }
    
    private void transplant(RedBlackNode u, RedBlackNode v) {
        if (u.parent == nil) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }
    private void deleteFixup(RedBlackNode node) {
        while (node != root && node.color == Color.BLACK) {
            if (node == node.parent.left) {
                RedBlackNode sibling = node.parent.right;
    
                // Case 1: Sibling is red
                if (sibling.color == Color.RED) {
                    sibling.color = Color.BLACK;
                    node.parent.color = Color.RED;
                    leftRotate(node.parent);
                    sibling = node.parent.right;
                }
    
                // Case 2: Sibling is black with two black children
                if (sibling.left.color == Color.BLACK && sibling.right.color == Color.BLACK) {
                    sibling.color = Color.RED;
                    node = node.parent;
                } else {
                    // Case 3: Sibling is black with a red left child and a black right child
                    if (sibling.right.color == Color.BLACK) {
                        sibling.left.color = Color.BLACK;
                        sibling.color = Color.RED;
                        rightRotate(sibling);
                        sibling = node.parent.right;
                    }
    
                    // Case 4: Sibling is black with a red right child
                    sibling.color = node.parent.color;
                    node.parent.color = Color.BLACK;
                    sibling.right.color = Color.BLACK;
                    leftRotate(node.parent);
                    node = root;
                }
            } else {
                RedBlackNode sibling = node.parent.left;
    
                // Case 1: Sibling is red
                if (sibling.color == Color.RED) {
                    sibling.color = Color.BLACK;
                    node.parent.color = Color.RED;
                    rightRotate(node.parent);
                    sibling = node.parent.left;
                }
    
                // Case 2: Sibling is black with two black children
                if (sibling.right.color == Color.BLACK && sibling.left.color == Color.BLACK) {
                    sibling.color = Color.RED;
                    node = node.parent;
                } else {
                    // Case 3: Sibling is black with a red right child and a black left child
                    if (sibling.left.color == Color.BLACK) {
                        sibling.right.color = Color.BLACK;
                        sibling.color = Color.RED;
                        leftRotate(sibling);
                        sibling = node.parent.left;
                    }
    
                    // Case 4: Sibling is black with a red left child
                    sibling.color = node.parent.color;
                    node.parent.color = Color.BLACK;
                    sibling.left.color = Color.BLACK;
                    rightRotate(node.parent);
                    node = root;
                }
            }
        }
        node.color = Color.BLACK;
    }
    
}

    private static final String OUTPUT_FILE = "output_file.txt";

    public static void main(String[] args) throws IOException {
        gatorTaxi GatorTaxi = new gatorTaxi();
        RedBlackTree redblacktree = new RedBlackTree();
        String fileName = args[0];

        MinHeap minHeap = new MinHeap();
        RedBlackTree redBlackTree = new RedBlackTree();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName));
             PrintWriter writer = new PrintWriter(new FileWriter(OUTPUT_FILE))) {

            String line;
            Pattern pattern = Pattern.compile("\\(([^)]+)\\)");
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String[] parameters = matcher.group(1).split(",");
                    switch (line.split("\\(")[0]) {
                        case "Insert":
                            int rideNumber = Integer.parseInt(parameters[0]);
                            int rideCost = Integer.parseInt(parameters[1]);
                            int tripDuration = Integer.parseInt(parameters[2]);
                            Ride ride = new Ride(rideNumber, rideCost, tripDuration);
                            //System.out.println(ride.toString());
                            if(redblacktree.repeat(ride)){
                                writer.println("Duplicate Ride Number");
                            }
                            if (!minHeap.insert(ride)) {
                                writer.println("Duplicate Ride Number");
                            }
                            else 
                            redblacktree.insert(ride);
                            break;
                        case "Print":
                            if (parameters.length == 1) {
                                writer.println(redblacktree.printRide(Integer.parseInt(parameters[0])));
                            } else {
                                writer.println(redblacktree.printRidesInRange(Integer.parseInt(parameters[0]), Integer.parseInt(parameters[1])));
                            }
                            break;
                        case "UpdateTrip":
                            int newTripDuration = Integer.parseInt(parameters[1]);
                            redblacktree.updateTrip(Integer.parseInt(parameters[0]), newTripDuration, minHeap);
                            break;
                        case "CancelRide":
                            redblacktree.delete(Integer.parseInt(parameters[0]));
                            minHeap.delete(Integer.parseInt(parameters[0]));
                            break;
                    }
                }
                if(line.equals("GetNextRide()"))
                {
                    Ride nextRide = minHeap.extractMin(redblacktree);
                            if (nextRide != null) {
                                writer.println(nextRide);
                            } else {
                                writer.println("No active ride requests");
                            }
                }
            }
        }
    }
}
