package lesson4;

import java.util.*;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Префиксное дерево для строк
 */
public class Trie extends AbstractSet<String> implements Set<String> {

    private static class Node {
        SortedMap<Character, Node> children = new TreeMap<>();
    }

    private final Node root = new Node();

    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root.children.clear();
        size = 0;
    }

    private String withZero(String initial) {
        return initial + (char) 0;
    }

    @Nullable
    private Node findNode(String element) {
        Node current = root;
        for (char character : element.toCharArray()) {
            if (current == null) return null;
            current = current.children.get(character);
        }
        return current;
    }

    @Override
    public boolean contains(Object o) {
        String element = (String) o;
        return findNode(withZero(element)) != null;
    }

    @Override
    public boolean add(String element) {
        Node current = root;
        boolean modified = false;
        for (char character : withZero(element).toCharArray()) {
            Node child = current.children.get(character);
            if (child != null) {
                current = child;
            } else {
                modified = true;
                Node newChild = new Node();
                current.children.put(character, newChild);
                current = newChild;
            }
        }
        if (modified) {
            size++;
        }
        return modified;
    }

    @Override
    public boolean remove(Object o) {
        String element = (String) o;
        Node current = findNode(element);
        if (current == null) return false;
        if (current.children.remove((char) 0) != null) {
            size--;
            return true;
        }
        return false;
    }

    /**
     * Итератор для префиксного дерева
     *
     * Спецификация: {@link Iterator} (Ctrl+Click по Iterator)
     *
     * Сложная
     */
    @NotNull
    @Override
    public Iterator<String> iterator() {
        return new TrieIterator();
    }

    public class TrieIterator implements Iterator<String> {
        private final List<String> list;
        private final int initSize;
        private int numberOfPassedNodes;
        private String currentNode;

        //Сложность O(N)
        private TrieIterator() {
            list = new ArrayList<>();
            initSize = size();
            numberOfPassedNodes = 0;
            addWordsInList("", root);
        }

        //Сложность O(1)
        //Память O(1)
        @Override
        public boolean hasNext() {
            return numberOfPassedNodes < initSize;
        }

        //Сложность O(1)
        //Память O(1)
        @Override
        public String next() {
            if (numberOfPassedNodes == initSize) throw new NoSuchElementException();
            currentNode = list.get(numberOfPassedNodes);
            numberOfPassedNodes++;
            return currentNode;
        }

        //Сложность O(максимальная длина слова)
        //Память O(1)
        @Override
        public void remove() {
            if (currentNode == null) throw new IllegalStateException();
            Trie.this.remove(currentNode);
            currentNode = null;
        }

        private void addWordsInList(String str, Node node) {
            for (char character: node.children.keySet()) {
                if (character != (char) 0) {
                    addWordsInList(str + character, node.children.get(character));
                }
                else list.add(str);
            }
        }

    }

}