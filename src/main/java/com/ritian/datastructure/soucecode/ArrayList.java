package com.ritian.datastructure.soucecode;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * ArrayList源码解析
 * 是基于 数组实现的 List 实现类，支持在数组容量不够时，一般按照 1.5 倍自动扩容。同时，它支持手动扩容、手动缩容
 * <p>
 * 知识点：
 * 1.{@link #DEFAULTCAPACITY_EMPTY_ELEMENTDATA} 首次扩容为 10 ，而 {@link #EMPTY_ELEMENTDATA }按照 1.5 倍扩容从 0 开始而不是 10
 * 2.理解函数用法 System.arraycopy()
 * 3.ArrayList 随机访问时间复杂度是 O(1) ，查找指定元素的平均时间复杂度是 O(n) 。
 * @author ritian
 * @since 2020/1/4 11:30
 **/
@Slf4j
public class ArrayList<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable {

    private static final long serialVersionUID = -8994033090781481152L;

    /**
     * 默认容量大小
     * <p>
     * Default initial capacity.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 共享的空数组对象。
     * 在 {@link #ArrayList(int)} 或 {@link #ArrayList(Collection)} 构造方法中，
     * 如果传入的初始化大小或者集合大小为 0 时，将 {@link #elementData} 指向它。
     * Shared empty array instance used for empty instances.
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     * 共享的空数组对象，用于 {@link #ArrayList()} 构造方法。
     * <p>
     * 通过使用该静态变量，和 {@link #EMPTY_ELEMENTDATA} 区分开来，在第一次添加元素时。
     * <p>
     * Shared empty array instance used for default sized empty instances. We
     * distinguish this from EMPTY_ELEMENTDATA to know how much to inflate when
     * first element is added.
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    /**
     * 元素数组
     * 当添加新的元素时，如果该数组不够，会创建新的数组，并将原数组拷贝到新的数组。之后将该变量指向新数组
     * <p>
     * The array buffer into which the elements of the ArrayList are stored.
     * The capacity of the ArrayList is the length of this array buffer. Any
     * empty ArrayList with elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA
     * will be expanded to DEFAULT_CAPACITY when the first element is added.
     */
    transient Object[] elementData; // non-private to simplify nested class access

    /**
     * 已使用的数组大小
     * <p>
     * The size of the ArrayList (the number of elements it contains).
     *
     * @serial
     */
    private int size;

    public ArrayList(int initialCapacity) {
        // 初始化容量大于 0 时，创建 Object 数组
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
            // 初始化容量等于 0 时，使用 EMPTY_ELEMENTDATA 对象
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
            // 初始化容量小于 0 时，抛出 IllegalArgumentException 异常
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " +
                    initialCapacity);
        }
    }

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    /**
     * Constructs a list containing the elements of the specified
     * collection, in the order they are returned by the collection's
     * iterator.
     *
     * @param c the collection whose elements are to be placed into this list
     * @throws NullPointerException if the specified collection is null
     */
    public ArrayList(Collection<? extends E> c) {
        elementData = c.toArray();
        if ((size = elementData.length) != 0) {
            // c.toArray might (incorrectly) not return Object[] (see 6260652)
//            if (elementData.getClass() != Object[].class)
//                elementData = Arrays.copyOf(elementData, size, Object[].class);
        } else {
            // replace with empty array.
            this.elementData = EMPTY_ELEMENTDATA;
        }
    }

    /**
     * Trims the capacity of this <tt>ArrayList</tt> instance to be the
     * list's current size.  An application can use this operation to minimize
     * the storage of an <tt>ArrayList</tt> instance.
     */
    public void trimToSize() {
        modCount++;
        if (size < elementData.length) {
            elementData = (size == 0)
                    ? EMPTY_ELEMENTDATA
                    : Arrays.copyOf(elementData, size);
        }
    }

    /**
     * 保证内部容量
     */
    private void ensureCapacityInternal(int minCapacity) {
        log.info("minCapacity is {}", minCapacity);
        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
    }

    /**
     * 计算容量
     */
    private static int calculateCapacity(Object[] elementData, int minCapacity) {
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            log.info("elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA");
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        return minCapacity;
    }

    /**
     * 保证扩展容量
     */
    private void ensureExplicitCapacity(int minCapacity) {
        modCount++;

        // overflow-conscious code
        if (minCapacity - elementData.length > 0) {
            log.info("elementData length is【{}】,minCapacity is 【{}】,need grow", elementData.length, minCapacity);
            grow(minCapacity);
        }
    }

    /**
     * The maximum size of array to allocate.
     * Some VMs reserve some header words in an array.
     * Attempts to allocate larger arrays may result in
     * OutOfMemoryError: Requested array size exceeds VM limit
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    /**
     * 扩容
     * <p>
     * Increases the capacity to ensure that it can hold at least the
     * number of elements specified by the minimum capacity argument.
     *
     * @param minCapacity the desired minimum capacity
     */
    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0) {
            newCapacity = minCapacity;
        }
        if (newCapacity - MAX_ARRAY_SIZE > 0) {
            newCapacity = hugeCapacity(minCapacity);
            // minCapacity is usually close to size, so this is a win:
        }
        log.info("newCapacity is {}", newCapacity);
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    /**
     * 巨大容量
     */
    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }


    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns <tt>true</tt> if this list contains no elements.
     *
     * @return <tt>true</tt> if this list contains no elements
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns <tt>true</tt> if this list contains the specified element.
     * More formally, returns <tt>true</tt> if and only if this list contains
     * at least one element <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
     *
     * @param o element whose presence in this list is to be tested
     * @return <tt>true</tt> if this list contains the specified element
     */
    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    /**
     * Returns the index of the first occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     * More formally, returns the lowest index <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
     * or -1 if there is no such index.
     */
    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++){
                if (elementData[i] == null)
                {
                    return i;
                }
            }

        } else {
            for (int i = 0; i < size; i++){
                if (o.equals(elementData[i]))
                { return i;}
            }

        }
        return -1;
    }

    /**
     * Returns the index of the last occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     * More formally, returns the highest index <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
     * or -1 if there is no such index.
     */
    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--){
                if (elementData[i] == null)
                {return i;}
            }
        } else {
            for (int i = size - 1; i >= 0; i--){
                if (o.equals(elementData[i])){
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Returns a shallow copy of this <tt>ArrayList</tt> instance.  (The
     * elements themselves are not copied.)
     *
     * @return a clone of this <tt>ArrayList</tt> instance
     */
    @Override
    public Object clone() {
        try {
            ArrayList<?> v = (ArrayList<?>) super.clone();
            v.elementData = Arrays.copyOf(elementData, size);
            v.modCount = 0;
            return v;
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
    }

    /**
     * Returns an array containing all of the elements in this list
     * in proper sequence (from first to last element).
     *
     * <p>The returned array will be "safe" in that no references to it are
     * maintained by this list.  (In other words, this method must allocate
     * a new array).  The caller is thus free to modify the returned array.
     *
     * <p>This method acts as bridge between array-based and collection-based
     * APIs.
     *
     * @return an array containing all of the elements in this list in
     * proper sequence
     */
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    /**
     * Returns an array containing all of the elements in this list in proper
     * sequence (from first to last element); the runtime type of the returned
     * array is that of the specified array.  If the list fits in the
     * specified array, it is returned therein.  Otherwise, a new array is
     * allocated with the runtime type of the specified array and the size of
     * this list.
     *
     * <p>If the list fits in the specified array with room to spare
     * (i.e., the array has more elements than the list), the element in
     * the array immediately following the end of the collection is set to
     * <tt>null</tt>.  (This is useful in determining the length of the
     * list <i>only</i> if the caller knows that the list does not contain
     * any null elements.)
     *
     * @param a the array into which the elements of the list are to
     *          be stored, if it is big enough; otherwise, a new array of the
     *          same runtime type is allocated for this purpose.
     * @return an array containing the elements of the list
     * @throws ArrayStoreException  if the runtime type of the specified array
     *                              is not a supertype of the runtime type of every element in
     *                              this list
     * @throws NullPointerException if the specified array is null
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size){
            // Make a new array of a's runtime type, but my contents:
            return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        }
        System.arraycopy(elementData, 0, a, 0, size);
        if (a.length > size){
            a[size] = null;
        }
        return a;
    }

    // Positional Access Operations

    @SuppressWarnings("unchecked")
    E elementData(int index) {
        return (E) elementData[index];
    }


    /**
     * Appends the specified element to the end of this list.
     *
     * @param e element to be appended to this list
     * @return <tt>true</tt> (as specified by {@link Collection#add})
     */
    @Override
    public boolean add(E e) {
        //jdk8后有变化
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        elementData[size++] = e;
        return true;
    }
    /**
     * Inserts the specified element at the specified position in this
     * list. Shifts the element currently at that position (if any) and
     * any subsequent elements to the right (adds one to their indices).
     *
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);

        ensureCapacityInternal(size + 1);  // Increments modCount!!
        System.arraycopy(elementData, index, elementData, index + 1,
                size - index);
        elementData[index] = element;
        size++;
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their
     * indices).
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public E remove(int index) {
        // 校验 index 不要超过 size
        rangeCheck(index);

        modCount++;
        E oldValue = elementData(index);

        int numMoved = size - index - 1;
        //如果 i 不是移除最末尾的元素，则将 i + 1 位置的数组往前挪
        if (numMoved > 0){
            System.arraycopy(elementData, index+1, elementData, index,
                    numMoved);
        }
        // clear to let GC do its work  将新的末尾置为 null ，帮助 GC
        elementData[--size] = null;

        // 返回该位置的原值
        return oldValue;
    }

    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present.  If the list does not contain the element, it is
     * unchanged.  More formally, removes the element with the lowest index
     * <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>
     * (if such an element exists).  Returns <tt>true</tt> if this list
     * contained the specified element (or equivalently, if this list
     * changed as a result of the call).
     *
     * @param o element to be removed from this list, if present
     * @return <tt>true</tt> if this list contained the specified element
     */
    @Override
    public boolean remove(Object o) {
        if (o == null) {
            for (int index = 0; index < size; index++){
                if (elementData[index] == null) {
                    fastRemove(index);
                    return true;
                }
            }

        } else {
            for (int index = 0; index < size; index++){
                if (o.equals(elementData[index])) {
                    fastRemove(index);
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * Private remove method that skips bounds checking and does not
     * return the value removed.
     */
    private void fastRemove(int index) {
        modCount++;
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index,
                    numMoved);
        elementData[--size] = null; // clear to let GC do its work
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public E get(int index) {
        rangeCheck(index);

        return elementData(index);
    }

    /**
     * Checks if the given index is in range.  If not, throws an appropriate
     * runtime exception.  This method does *not* check if the index is
     * negative: It is always used immediately prior to an array access,
     * which throws an ArrayIndexOutOfBoundsException if index is negative.
     */
    private void rangeCheck(int index) {
        if (index >= size){
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }
    /**
     * A version of rangeCheck used by add and addAll.
     */
    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0){
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    /**
     * Constructs an IndexOutOfBoundsException detail message.
     * Of the many possible refactorings of the error handling code,
     * this "outlining" performs best with both server and client VMs.
     */
    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    public static void main(String[] args) {
        ArrayListTest test = new ArrayListTest();
//        test.testAdd();
        test.testAdd2();


    }

    static class ArrayListTest {

        /**
         * <a href="https://bugs.java.com/bugdatabase/view_bug.do?bug_id=6260652">bug</a>
         */
        public void test6260652() {
            List<Integer> list = Arrays.asList(1, 2);
            // JDK8 返回 Integer[] 数组，JDK9+ 返回 Object[] 数组。
            Object[] array = list.toArray();
            System.out.println("array className:" + array.getClass());
            // 此处，在 JDK8 和 JDK9+ 表现不同，前者会报 ArrayStoreException 异常，后者不会。
            array[0] = new Object();
        }

        /**
         * 在我们学习 ArrayList 的时候，一直被灌输了一个概念，在未设置初始化容量时，
         * ArrayList 默认大小为 10 。但是此处，我们可以看到初始化为 DEFAULTCAPACITY_EMPTY_ELEMENTDATA 这个空数组。
         * 这是为什么呢？ArrayList 考虑到节省内存，一些使用场景下仅仅是创建了 ArrayList 对象，实际并未使用。
         * 所以，ArrayList 优化成初始化是个空数组，在首次添加元素时，才真正初始化为容量为 10 的数组。
         * <p>
         * 那么为什么单独声明了 DEFAULTCAPACITY_EMPTY_ELEMENTDATA 空数组，而不直接使用 EMPTY_ELEMENTDATA 呢？
         * 在下文中，我们会看到 DEFAULTCAPACITY_EMPTY_ELEMENTDATA 首次扩容为 10 ，而 EMPTY_ELEMENTDATA 按照 1.5 倍扩容从 0 开始而不是 10 。
         * 😈 两者的起点不同，嘿嘿
         */
        public void test2() {

        }

        /**
         * 测试当elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA时 如何扩容
         * 1.第一次添加元素时扩容至 {@link #DEFAULT_CAPACITY}
         * 后面再添加元素当超过{@link #elementData}大小时，newCapacity = old + old >> 1; 即1.5倍增长
         */
        public void testAdd() {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 11; i++) {
                list.add("1");
            }
        }

        public void testAdd2() {
            List<String> list = new ArrayList<>(0);
            for (int i = 0; i < 10; i++) {
                list.add(i + "");
                System.out.println("#################" + ((ArrayList<String>) list).elementData.length);
            }

        }
    }


}
