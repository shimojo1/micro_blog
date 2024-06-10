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
public interface FavoriteMapper {
    // 参考にしていたもの
    // https://blog.interfamilia.co.jp/2022/07/14/MyBatis-%E3%81%AE%E3%82%A2%E3%83%8E%E3%83%86%E3%83%BC%E3%82%B7%E3%83%A7%E3%83%B3%E8%A8%98%E6%B3%95%E3%82%92%E4%BD%BF%E3%81%A3%E3%81%9F%E3%83%8D%E3%82%B9%E3%83%88%E3%81%95%E3%82%8C%E3%81%9F%E3%82%AA%E3%83%96%E3%82%B8%E3%82%A7%E3%82%AF%E3%83%88%E3%81%AE%E3%83%9E%E3%83%83%E3%83%94%E3%83%B3%E3%82%B0/
    @Select("""
            SELECT
                *
            FROM
                FAVORITE
            """)
    List<Favorite> findAll();
    
    @Select("""
            SELECT
                *
            FROM
                FAVORITE
            WHERE
                id = #{id}
            """)
    Favorite findById(Integer id);

    @Select("""
            SELECT
                  id
                , user_id
                , tweet_id
                , modified_at
            FROM
                  FAVORITE
            WHERE
                user_id = #{id}
            """)
    @Results(id = "favoriteDto", value = {
            @Result(id = true, column = "modified_at", property = "modifiedAt"),
            @Result(id = true, column = "id", property = "id"),
            @Result(id = true, column = "user_id", property = "user", one = @One(select = "selectUserByUserId"), javaType = User.class),
            @Result(id = true, column = "tweet_id", property = "tweet", one = @One(select = "selectTweetByTweetId"), javaType = Tweet.class),
    })
    List<Favorite> findByUserId(Integer id);
    
    // TODO: 動かないので、同じファイル内に移動してきた
    @Select("SELECT id, mail, nickname FROM USERS where id = #{id}")
    @Results(id = "User", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class),
            @Result(id = true, column = "mail", property = "mail", javaType = String.class),
            @Result(id = true, column = "nickname", property = "nickname", javaType = String.class),
    })
    User selectUserByUserId(Integer id);

    @Select("SELECT id, body, user_id, created_at FROM TWEET WHERE id = #{id}")
    @Results(id = "Tweet", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class),
            @Result(id = true, column = "body", property = "body", javaType = String.class),
            @Result(id = true, column = "user_id", property = "user", one = @One(select = "selectUserByUserId"), javaType = User.class),
            @Result(id = true, column = "created_at", property = "createdAt", javaType = Date.class),
            
    })
    Tweet selectTweetByTweetId(Integer id);
    // ./TODO

    @Select("select * from favorite where id = #{id}")
    Integer selectFavoriteId(Integer id);
    
    
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
}
