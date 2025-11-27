package ru.netology.nmedia.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ru.netology.nmedia.Entity.PostEntity

@Dao
interface PostDao {
    @Query("SELECT* FROM PostEntity ORDER BY ID DESC ")
    fun getAll(): LiveData<List<PostEntity>>

    @Upsert
    fun save(post: PostEntity): Long

    @Query("UPDATE PostEntity SET content = :content, video = :video WHERE id = :id")
    fun edit(id: Long?, content: String, video: String?)

    @Query(
        "UPDATE PostEntity SET\n" +
                "               likes = likes + CASE WHEN likedByMe THEN -1 ELSE 1 END,\n" +
                "               likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END\n" +
                "           WHERE id = :id;"
    )
    fun likeById(id: Long)

    @Query("DELETE FROM PostEntity WHERE id = :id")
    fun removeById(id: Long)

    @Query(" UPDATE  PostEntity SET share = share + 1  WHERE id = :id;")
    fun share(id: Long)

}