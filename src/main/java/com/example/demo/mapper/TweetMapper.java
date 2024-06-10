package com.example.demo.mapper;

import com.example.demo.dto.FavoriteDto;
import com.example.demo.dto.TweetDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Favorite;
import com.example.demo.entity.Tweet;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface TweetMapper {
    @Select("""
            SELECT
                *
            FROM
                USERS
            """)
    List<Favorite> findAll();
    
    @Select("""
            SELECT
                  id
                , user_id
                , body
                , created_at
            FROM
                TWEET
            WHERE
                id = #{id}
            """)
    UserDto findById(Integer id);
    

    @Select("""
            SELECT
                  id id
                , user_id userId
                , body body
                , created_at createdAt
            FROM
                  TWEET
            WHERE
                f.user_id = u.id
            ORDER BY
                created_at
            """)
    @Results(id = "favoriteDto", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(id = true, column = "useId", property = "useId"),
            @Result(id = true, column = "body", property = "body"),
            @Result(id = true, column = "createdAt", property = "createdAt"),
            
    })
    List<TweetDto> findByUserIdOrderByCreatedAtDesc(Integer id);
    
    
    
    @Insert("""
            INSERT INTO
                Tweet
            (
                  user_id
                , body
                , created_at
            ) VALUES (
                  #{userId}
                , #{tweetId}
                , #{now}
            )
            """)
    void insert(Integer userId, Integer tweetId, Date now);

    // TODO: セット内容未定
    @Update("""
            UPDATE
                Tweet
            SET
                ""=""
            """)
    void update(Favorite favorite);
    
    @Delete("""
            DELETE FROM
                Tweet
            WHERE
                id = #{id}
            """)
    void deleteById(Integer id);


    @Select("SELECT id, body, user_id, created_at FROM TWEET WHERE id = #{id}")
    @Results(id = "Tweet", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class),
            @Result(id = true, column = "body", property = "body", javaType = String.class),
            @Result(id = true, column = "user_id", property = "user", one = @One(select = "selectUserByUserId"), javaType = User.class),
            @Result(id = true, column = "created_at", property = "createdAt", javaType = Date.class),

    })
    Tweet selectTweetByTweetId(Integer id);
}
