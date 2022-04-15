package com.demon.base.list

/**
 * @author DeMon
 * Created on 2022/4/14.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
class Items : ArrayList<Any> {
    /**
     * Constructs an empty Items with an initial capacity of ten.
     */
    constructor() : super() {}

    /**
     * Constructs an empty Items with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the Items
     * @throws IllegalArgumentException if the specified initial capacity
     * is negative
     */
    constructor(initialCapacity: Int) : super(initialCapacity) {}

    /**
     * Constructs a Items containing the elements of the specified
     * collection, in the order they are returned by the collection's
     * iterator.
     *
     * @param c the collection whose elements are to be placed into this Items
     * @throws NullPointerException if the specified collection is null
     */
    constructor(c: Collection<*>) : super(c) {}
}