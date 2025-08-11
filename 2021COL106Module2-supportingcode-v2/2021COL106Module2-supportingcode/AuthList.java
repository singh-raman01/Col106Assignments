import javax.naming.AuthenticationException;

import Includes.*;

public class AuthList{
	// PLEASE USE YOUR ENTRY NUMBER AS THE START STRING
	public static final String start_string = "2020EE10536";
	public Node firstNode;
	public Node lastNode;

	/*
		Note that the Exceptions have already been defined for you in the includes file,
		you just have to raise them accordingly

	*/

	/* 
	Notice that this function is static, the reason why this is static is that we don't want this to be tied with
	an object of the class AuthList. 	
	*/
	public static boolean CheckList(AuthList current, String proof) throws AuthenticationFailedException {
		CRF obj = new CRF(64);
		Node curr = current.firstNode;
		boolean initial = true;
		while(curr != null){
			if(initial){
				String hsh = obj.Fn(AuthList.start_string + "#" + curr.data.value);
				if(!curr.dgst.equals(hsh)) {
					throw new AuthenticationFailedException();
				}
				initial = false;
				curr = curr.next;
			}else if(curr == current.lastNode){
				String hsh = obj.Fn(curr.previous.dgst + "#" + curr.data.value);
				if(!curr.dgst.equals(hsh) || !curr.dgst.equals(proof)) {
					throw new AuthenticationFailedException();
				}
				curr = curr.next;
			}else{
				String hsh = obj.Fn(curr.previous.dgst + "#" + curr.data.value);
				if(!curr.dgst.equals(hsh))  {
					throw new AuthenticationFailedException();
				}
				curr = curr.next;
			}
		}
		return true;
	}


	public String InsertNode(Data datainsert, String proof) throws AuthenticationFailedException {
		CRF obj = new CRF(64);
		String output = "";

			if(CheckList(this, proof)){

				if(this.firstNode==this.lastNode && this.lastNode==null){
					String dgstInput = start_string + "#" + datainsert.value;
					output = obj.Fn(dgstInput); 
					Node addNode = new Node(null, null, datainsert, output);
					this.firstNode = addNode;
					this.lastNode = addNode;
				}
				else{
					String dgstInput = this.lastNode.dgst + "#" + datainsert.value;
					output = obj.Fn(dgstInput);
					Node addNode = new Node(this.lastNode,null,datainsert,output);
					this.lastNode.next = addNode;
					this.lastNode = addNode;
				}
			}
		return output;

	}

	public String DeleteFirst(String proof) throws AuthenticationFailedException, EmptyListException {
		/*
			Implement Code here
			// deleting the first node means the dgst for all the nodes will change
			// cases possible 
			// 0 node / throw exception
			// 1 node / remove it, now list is empty thus we get exception or null when we want to return digest?
			// 2 node / then lastNode becomes firstNode, and dgst get's changed
			// 3 node / then first node is the second node, and dgst gets changed for all nodes
		*/
		CRF obj = new CRF(64);

		if (this.firstNode==null){
			throw new EmptyListException();
		}else{
			if(CheckList(this, proof)){
				// deelting means changing and modifying all the digests,
				// means changing the firstNode in authList
				// that's it
				// how?
				this.firstNode = this.firstNode.next;
				if(this.firstNode==null){
					return null; // check if we have to throw an exception instead
				}else{
					this.firstNode.previous = null;
					String dgstInput = start_string + "#" + this.firstNode.data.value;
					String dgst = obj.Fn(dgstInput);
					this.firstNode.dgst = dgst;
					Node temp_node = this.firstNode;
					while(temp_node.next!=null){
						Node next_node = temp_node.next;
						String tempdDgstInput = temp_node.dgst + "#" + next_node.data.value;
						String tempDgst = obj.Fn(tempdDgstInput);
						next_node.dgst = tempDgst;
						temp_node=temp_node.next;
					}
					return temp_node.dgst;
				}
			}
		}
		return null;

	}


	public String DeleteLast(String proof) throws AuthenticationFailedException, EmptyListException {
		/*
			Implement Code here
			deleting the last node only changes the last dgst and nothing else, which is quite easy
		*/

		if(this.firstNode==null){
			throw new EmptyListException();
		}else{
			if(CheckList(this, proof)){
				Node last_node = this.lastNode;
				if(last_node.previous ==null){
					this.firstNode=null;
					this.lastNode=null;
					return null;
				}else{
					this.lastNode=this.lastNode.previous;
					this.lastNode.next=null;
					return this.lastNode.dgst;
				}
			}
		}

		return null;
	}

	/* 
	Notice that this function is static, the reason why this is static is that we don't want this to be tied with
	an object of the class AuthList. 	
	*/
	public static Node RetrieveNode(AuthList current, String proof, Data data) throws AuthenticationFailedException, DocumentNotFoundException{
		/*
			Implement Code here
		*/

		if(CheckList(current, proof)){
			Node temp_node = current.firstNode;
			while(temp_node!=null){
				if(temp_node.data==data){
					return temp_node;
				}else{
					temp_node = temp_node.next;
				}
			}
			throw new DocumentNotFoundException();
		}

		return null;
	}

	public void AttackList(AuthList current, String new_data)throws EmptyListException{
		/*
			Implement Code here
		*/
	}

}
