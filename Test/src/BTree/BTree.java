package BTree;

import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class BTree {

	public String value;
	public BTree left;
	public BTree right;
	
	public BTree(String s){value=s;}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public BTree getLeft() {
		return left;
	}

	public void setLeft(BTree left) {
		this.left = left;
	}

	public BTree getRight() {
		return right;
	}

	public void setRight(BTree right) {
		this.right = right;
	}
	
	public void depthFirst(BTree node){
		System.out.println(node.getValue());
		if(node.getLeft()==null && node.getRight()==null)
			return;
		if(node.getLeft() != null)
			depthFirst(node.getLeft());
		if(node.getRight() != null)
			depthFirst(node.getRight());
	}
	
	public void breadthFirst(BTree node, int depthOfTree){
		List<BTree> list = new LinkedList<BTree>();
		list.add(node);
		Map<Integer, List<BTree>>  m = new HashMap<Integer, List<BTree>>();
		int depth = 0;
		m.put(Integer.valueOf(depth), list);
		
		while(true){
			List<BTree> childList = new LinkedList<BTree>();
			//if(list.size()==0)
			//	break;
			for(int i=0; i<list.size();i++){
				BTree current = list.get(i);
				System.out.println("transversing " + current.getValue());
				if (current.getLeft()!=null)
					childList.add(current.getLeft());
				if (current.getRight()!=null)
					childList.add(current.getRight());
			}
			//depth++;
			if(childList.size()!=0)
				m.put(Integer.valueOf(++depth), childList);
			else
				break;
			list = childList;
		}

		int i=0;
		
		if(depthOfTree == -1)
			while(true){
				if(!m.containsKey(Integer.valueOf(i)))
					break;
				List<BTree> depthList = m.get(Integer.valueOf(i));
				System.out.print("At depth " + i + ": ");
				for ( BTree n:depthList){
					System.out.print(n.getValue() + " ");
				}
				System.out.println();
				i++;
			}
		else{
			if(!m.containsKey(Integer.valueOf(depthOfTree))){
				System.out.print("No element at such depth - " + depthOfTree);
				return;
			}
				List<BTree> depthList = m.get(Integer.valueOf(depthOfTree));
			System.out.print("At depth " + depthOfTree + ": ");
			for ( BTree n:depthList){
				System.out.print(n.getValue() + " ");
			}
			System.out.println();
		}
			
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		BTree root = new BTree("1");
		
		BTree node =  new BTree("2");
		root.setLeft(node);
		node =  new BTree("3");
		root.setRight(node);
		node =  new BTree("4");
		root.left.setLeft(node);
		node =  new BTree("5");
		root.left.setRight(node);
		node =  new BTree("5.1");
		root.left.right.setLeft(node);
		node =  new BTree("5.2");
		root.left.right.setRight(node);
		root.left.right.left.setRight(node);
		node =  new BTree("6");
		root.right.setLeft(node);
		node =  new BTree("7");
		root.right.setRight(node);
		
		//root.depthFirst(root);
		root.breadthFirst(root, 5);
	}

}
