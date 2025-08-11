import Includes.*;

public class Node{
	public Data data;
	/*
		Data has a String attribute, you can set the value of which 
		in the constructor or using some other function

		For more reference see the Data.java file
	*/
	public Node previous;
	public Node next;
	public String dgst;

	Node(Node prevNode, Node nextNode, Data inputData,String inputDigest){
		this.data = inputData;
		this.previous = prevNode;
		this.next = nextNode;
		this.dgst = inputDigest;
	}

	}

