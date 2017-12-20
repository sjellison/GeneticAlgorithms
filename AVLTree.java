package ga;

import java.util.ArrayList;

public class AVLTree <T extends Comparable<T>>
{
	
	Node<T> root;
	
	/**
	 * Constructor
	 * @param data
	 */
	public AVLTree(T data)
	{
		this.root = new Node<T>(data);
	}
	
	public AVLTree()
	{
		root = null;
	}
	
	public int getTreeHeight()
	{
		return getHeight(root);
	}
	
	public int getSize()
	{
		if(root == null)
		{
			return 0;
		}
		return root.numChildren + 1;
	}
	
	public Node<T> rotateLeft(Node<T> oldRoot)
	{
		Node<T> result = oldRoot.leftChild;
		oldRoot.leftChild = result.rightChild;
		result.rightChild = oldRoot;
		
		adjustHeight(oldRoot);
		adjustHeight(oldRoot.leftChild);
		adjustHeight(result);
		
		return result;
	}
	
	public Node<T> rotateRight(Node<T> oldRoot)
	{
		Node<T> result = oldRoot.rightChild;
		oldRoot.rightChild = result.leftChild;
		result.leftChild = oldRoot;
		
		adjustHeight(oldRoot);
		adjustHeight(oldRoot.rightChild);
		adjustHeight(result);
		
		return result;
	}
	
	public void adjustHeight(Node<T> n)
	{
		if(n != null)
		{
			n.height = 1 + Math.max(getHeight(n.leftChild), getHeight(n.rightChild));
			int z = 0;
			if(n.leftChild != null)
			{
				z += n.leftChild.numChildren + 1;
			}
			if(n.rightChild != null)
			{
				z += n.rightChild.numChildren + 1;
			}
			n.numChildren = z;
		}
	}
	
	public Node<T> doubleWithRightChild(Node<T> oldRoot)
	{
		oldRoot.rightChild = rotateLeft(oldRoot.rightChild);
		return rotateRight(oldRoot);
	}
	
	public Node<T> doubleWithLeftChild(Node<T> oldRoot)
	{
		oldRoot.leftChild = rotateRight(oldRoot.leftChild);
		return rotateLeft(oldRoot);
	}
	
	public Node<T> rightLeftRotation(Node<T> oldRoot)
	{
		Node<T> rChild = oldRoot.rightChild;
		Node<T> result = rChild.leftChild;
		oldRoot.rightChild = result.leftChild;
		rChild.leftChild = result.rightChild;
		result.leftChild = oldRoot;
		result.rightChild = rChild;
		
		adjustHeight(oldRoot);
		adjustHeight(rChild);
		adjustHeight(result);
		
		return result;
	}
	
	public Node<T> leftRightRotation(Node<T> oldRoot)
	{
		Node<T> lChild = oldRoot.leftChild;
		Node<T> result = lChild.rightChild;
		oldRoot.leftChild = result.rightChild;
		lChild.rightChild = result.leftChild;
		result.rightChild = oldRoot;
		result.leftChild = lChild;
		
		adjustHeight(oldRoot);
		adjustHeight(lChild);
		adjustHeight(result);
		
		return result;
	}
	
	//Inserts a new data point into the tree and rebalances the tree
	public void insert(T data)
	{
		if(root != null)
		{
			root = insertRec(data, root);
		}
		else
		{
			root = new Node<T>(data);
		}
	}
	
	private Node<T> insertRec(T data, Node<T> n)
	{
		if(n == null)
		{
			n = new Node<T>(data);
		}
		else if(data.compareTo(n.data) <= 0)
		{
			n.leftChild = insertRec(data, n.leftChild);
			if(getHeight(n.leftChild) - getHeight(n.rightChild) == 2)
			{
				if(data.compareTo(n.leftChild.data) <= 0)
				{
					n = rotateLeft(n);
				}
				else
				{
					n = leftRightRotation(n);
				}
			}
		}
		else //this should work just fine since equal cases are handled above
		{
			n.rightChild = insertRec(data, n.rightChild);
			if(getHeight(n.rightChild) - getHeight(n.leftChild) == 2)
			{
				if(data.compareTo(n.rightChild.data) > 0)
				{
					n = rotateRight(n);
				}
				else
				{
					n = rightLeftRotation(n);
				}
			}
		}
		
		n.height = 1 + Math.max(getHeight(n.leftChild), getHeight(n.rightChild));
		int newChildren = 0;
		if(n.leftChild != null)
		{
			newChildren += n.leftChild.numChildren + 1;
		}
		if(n.rightChild != null)
		{
			newChildren += n.rightChild.numChildren + 1;
		}
		n.numChildren = newChildren;
		return n;
	}
	
	public void getInOrder(ArrayList<T> arr)
	{
		inOrderRec(root, arr);
	}
	
	private void inOrderRec(Node<T> n, ArrayList<T> arr)
	{
		if(n == null)
		{
			return;
		}
		
		if(n.leftChild != null)
		{
			inOrderRec(n.leftChild, arr);
		}
		arr.add(n.data);
		if(n.rightChild != null)
		{
			inOrderRec(n.rightChild, arr);
		}
	}
	
	public void getPreOrder(ArrayList<T> arr)
	{
		preOrderRec(root, arr);
	}
	
	private void preOrderRec(Node<T> n, ArrayList<T> arr)
	{
		if(n == null)
		{
			return;
		}
		
		arr.add(n.data);
		if(n.leftChild != null)
		{
			preOrderRec(n.leftChild, arr);
		}
		if(n.rightChild != null)
		{
			preOrderRec(n.rightChild, arr);
		}
	}
	
	public void getpostOrder(ArrayList<T> arr)
	{
		postOrderRec(root, arr);
	}
	
	private void postOrderRec(Node<T> n, ArrayList<T> arr)
	{
		if(n == null)
		{
			return;
		}
		
		if(n.leftChild != null)
		{
			postOrderRec(n.leftChild, arr);
		}
		if(n.rightChild != null)
		{
			postOrderRec(n.rightChild, arr);
		}
		arr.add(n.data);
	}
	
	private int getHeight(Node<T> n)
	{
		if(n != null)
		{
			return n.height;
		}
		else
		{
			return 0;
		}
	}
	
	/**
	 * NODE CLASS
	 * @author Sean Jellison
	 *
	 * @param <K>
	 */
	private class Node <K extends Comparable<K>>
	{
		public K data;
		public Node<K> leftChild;
		public Node<K> rightChild;
		public int height;
		public int numChildren;
		
		public Node(K data)
		{
			this.data = data;
			leftChild = null;
			rightChild = null;
			height = 1;
			numChildren = 0;
		}
		
	} //end Node class
}
