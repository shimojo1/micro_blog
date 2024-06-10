package com.example.demo.dto;

import com.example.demo.entity.Tweet;
import com.example.demo.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
//@Getter
//@Setter
@Data
public class FavoriteDto {
    private Integer id;

    private User user;

    private Tweet tweet;
}
