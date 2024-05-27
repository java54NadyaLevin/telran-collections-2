package telran.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedList<T> implements List<T> {
	Node<T> head;
	Node<T> tail;
	int size;

	private static class Node<T> {
		T data;
		Node<T> prev;
		Node<T> next;

		Node(T data) {
			this.data = data;
		}
	}

	@Override
	public boolean add(T obj) {
		//O[N]?
		Node<T> node = new Node<>(obj);
		addNode(size, node);
		return true;
	}

	@Override
	public boolean remove(T pattern) {
		//O[N]? 
		int index = indexOf(pattern);
		boolean res = false;
		if (index > -1) {
			res = true;
			remove(index);
		}
		return res;
	}

	@Override
	public boolean contains(T pattern) {
		//O[N]
		return indexOf(pattern) == -1 ? false : true;
	}

	@Override
	public int size() {
		//O[1]
		return size;
	}

	@Override
	public Iterator<T> iterator() {
		//O[1]
		return new NodeIterator();
	}

	private class NodeIterator implements Iterator<T> {
		private Node<T> current = head;

		@Override
		public boolean hasNext() {
			//O[1]

			return current != null;
		}

		@Override
		public T next() {
			//O[1]
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			T data = current.data;
			current = current.next;

			return data;
		}

	}

	@Override
	public T get(int index) {
		//O[N] ?
		List.checkIndex(index, size, true);
		return getNode(index).data;
	}

	@Override
	public void add(int index, T obj) {
		//O[N]?
		List.checkIndex(index, size, false);
		Node<T> node = new Node<>(obj);
		addNode(index, node);

	}

	@Override
	public T remove(int index) {
		//O[N]? 
		List.checkIndex(index, size, true);
		T removedNode = removeNode(index);
		return removedNode;
	}

	private T removeNode(int index) {
		T removedNode = null;
		if (index == 0) {
			removedNode = removeHead(index);
		} else if (index == size - 1) {
			removedNode = removeTail(index);
		} else {
			removedNode = removeMiddle(index);
		}
		size--;
		return removedNode;
	}

	private T removeMiddle(int index) {

		Node<T> toRemove = getNode(index);
		T removedNode = toRemove.data;
		Node<T> nodePrev = toRemove.prev;
		Node<T> nodeNext = toRemove.next;
		nodePrev.next = nodeNext;
		nodeNext.prev = nodePrev;
		cleaner(toRemove);
		return removedNode;
	}

	private void cleaner(Node<T> toRemove) {
		toRemove.data = null;
		toRemove.prev = null;
		toRemove.next = null;
	}

	private T removeTail(int index) {
		Node<T> toRemove = getNode(index);
		T removedNode = toRemove.data;
		tail = tail.prev;
		tail.next = null;
		cleaner(toRemove);
		return removedNode;
	}

	private T removeHead(int index) {
		Node<T> toRemove = getNode(index);
		T removedNode = toRemove.data;
		head = head.next;
		head.prev = null;
		cleaner(toRemove);
		return removedNode;
	}

	@Override
	public int indexOf(T pattern) {
		//O[N]
		Node<T> current = head;

		int index = 0;
		while (current != null && !Objects.equals(current.data, pattern)) {
			index++;
			current = current.next;
		}

		return current != null ? index : -1;
	}

	@Override
	public int lastIndexOf(T pattern) {
		//O[N]
		Node<T> current = tail;

		int index = size - 1;
		while (current != null && !Objects.equals(current.data, pattern)) {
			index--;
			current = current.prev;
		}

		return current != null ? index : -1;

	}

	private Node<T> getNode(int index) {
		return index < size / 2 ? getNodeFromHead(index) : getNodeFromTail(index);
	}

	private Node<T> getNodeFromTail(int index) {
		Node<T> current = tail;
		for (int i = size - 1; i > index; i--) {
			current = current.prev;
		}
		return current;
	}

	private Node<T> getNodeFromHead(int index) {
		Node<T> current = head;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		return current;
	}

	private void addNode(int index, Node<T> node) {
		if (index == 0) {
			addHead(node);
		} else if (index == size) {
			addTail(node);
		} else {
			addMiddle(node, index);
		}
		size++;
	}

	private void addMiddle(Node<T> node, int index) {
		Node<T> nodeNext = getNode(index);
		Node<T> nodePrev = nodeNext.prev;
		nodeNext.prev = node;
		nodePrev.next = node;
		node.prev = nodePrev;
		node.next = nodeNext;

	}

	private void addTail(Node<T> node) {
		// head cannot be null
		tail.next = node;
		node.prev = tail;
		tail = node;

	}

	private void addHead(Node<T> node) {
		if (head == null) {
			head = tail = node;
		} else {
			node.next = head;
			head.prev = node;
			head = node;
		}

	}
}
