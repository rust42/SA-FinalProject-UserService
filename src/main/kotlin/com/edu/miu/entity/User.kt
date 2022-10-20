package com.edu.miu.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class User {
    @Id
    @GeneratedValue
    @Column(insertable = false, updatable = false)
    var id: Long? = null

    @Column(nullable = false, unique = true)
    var email: String = ""

    @Column(nullable = false)
    var firstName: String = ""

    @Column(nullable = false)
    var lastName: String = ""

    @Column(nullable = false)
    @JsonIgnore
    var password: String = ""

    @Enumerated(EnumType.STRING)
    var role: Role = Role.USER
}