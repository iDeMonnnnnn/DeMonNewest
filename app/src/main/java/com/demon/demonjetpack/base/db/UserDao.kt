package com.demon.demonjetpack.base.db

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * @author DeMon
 * Created on 2020/1/18.
 * E-mail 757454343@qq.com
 * Desc:
 */
@Dao
interface UserDao {

    /**
     * Observes list of users.
     *
     * @return all users.
     */
    @Query("SELECT * FROM users")
    fun observeUsers(): LiveData<List<User>>

    /**
     * Observes a single user.
     *
     * @param uid the user id.
     * @return the user with uid.
     */
    @Query("SELECT * FROM users WHERE id = :uid")
    fun observeUserByUid(uid: String): LiveData<User>

    /**
     * Select all users from the users table.
     *
     * @return all users.
     */
    @Query("SELECT * FROM users")
    suspend fun getUsers(): List<User>

    /**
     * Select a user by id.
     *
     * @param uid the user id.
     * @return the user with uid.
     */
    @Query("SELECT * FROM users WHERE id = :uid")
    suspend fun getUserByUid(uid: Int): User?

    /**
     * Insert a user in the database. If the user already exists, replace it.
     *
     * @param user the user to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    /**
     * Update a user.
     *
     * @param user user to be updated
     * @return the number of users updated. This should always be 1.
     */
    @Update
    suspend fun updateUser(user: User): Int


    /**
     * Delete a user by id.
     *
     * @return the number of users deleted. This should always be 1.
     */
    @Query("DELETE FROM users WHERE id = :uid")
    suspend fun deleteUserByUid(uid: Int): Int

    /**
     * Delete all users.
     */
    @Query("DELETE FROM users")
    suspend fun deleteUsers()
}