package com.endava.courseschedulingapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_app")
@ApiModel(description = "The entity used for User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "The username", example = "LoreClient", required = true)
    private String username;

    @ApiModelProperty(value = "The password", example = "pass12345678", required = true)
    private String password;

    @ApiModelProperty(value = "The email", example = "lore@client.com", required = true)
    private String email;

    @Column(name = "first_name")
    @ApiModelProperty(value = "The first name", example = "Lore", required = true)
    private String firstName;

    @Column(name = "last_name")
    @ApiModelProperty(value = "The last name", example = "Client", required = true)
    private String lastName;

    private boolean enabled;
    private boolean expired;
    private boolean locked;

    @Column(name = "credential_expired")
    private boolean credentialExpired;

    private String role;

    @ManyToMany(mappedBy = "user")
    @JsonIgnore
    private List<Course> course;
}
