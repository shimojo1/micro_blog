package com.example.demo.mapper;

import com.example.demo.dto.FavoriteDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Favorite;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UsersMapper {
    @Select("""
            SELECT
                *
            FROM
                USERS
            """)
    List<Favorite> findAll();
    
    @Select("""
            SELECT
                *
            FROM
                USERS
            WHERE
                id = #{id}
            """)
    UserDto findById(Integer id);
    

    @Select("""
            SELECT
                  f.id favoriteId
                , f.user_id userId
                , f.tweet_id tweetId
            FROM
                  FAVORITE f
            WHERE
                f.user_id = u.id
            """)
    @Results(id = "favoriteDto", value = {
            @Result(id = true, column = "favoriteId", property = "id"),
            @Result(id = true, column = "useId", property = "userId"),
            @Result(id = true, column = "tweetId", property = "tweetId"),
    })
    List<FavoriteDto> findByUserId(Integer id);
    
    
    
    @Insert("""
            INSERT INTO
                FAVORITE
            (
                  user_id
                , tweet_id
            ) VALUES (
                  #{userId}
                , #{tweetId}
            )
            """)
    void insert(Integer userId, Integer tweetId);

    @Update("""
            UPDATE
                FAVORITE
            SET
                ""=""
            """)
    void update(Favorite favorite);
    
    @Delete("""
            DELETE FROM
                FAVORITE
            WHERE
                id = #{id}
            """)
    void deleteById(Integer id);


    @Select("SELECT id, mail, nickname FROM USERS where id = #{id}")
    @Results(id = "User", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class),
            @Result(id = true, column = "mail", property = "mail", javaType = String.class),
            @Result(id = true, column = "nickname", property = "nickname", javaType = String.class),
    })
    User selectUserByUserId(Integer id);
}
