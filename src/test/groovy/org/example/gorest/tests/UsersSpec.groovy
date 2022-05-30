package org.example.gorest.tests

import groovy.json.JsonSlurper
import org.apache.http.HttpStatus
import org.example.gorest.framework.RandomGenerator
import org.example.gorest.framework.Users
import org.spockframework.util.Assert
import spock.lang.Specification

class UsersSpec extends Specification{


    def "Create User"(String name ,String email ,String gender,String status ,int expected_code) {
        given: "I have user parameters"
        def userData = [name: name, email: email, gender: gender, status: status]

        when: "Register User"
        def response = Users.createUser(userData)

        println("Resgistred user")
        println(response.contentAsString )
        def createdUser = new JsonSlurper().parseText(response.contentAsString)


        then: "Validate Operation"
        response.statusCode == expected_code
        if(expected_code == HttpStatus.SC_CREATED) {
            verifyAll(createdUser) {
                userData.name == name
                userData.email == email
                userData.gender == gender
                userData.status == status
            }

            when: "Get User that was just created"
            def response2 = Users.getUser(createdUser.id)
            println("Get user")
            println(response2.contentAsString )

            def fetchedUser = new JsonSlurper().parseText(response2.contentAsString)


            then: "Validate Operation"
            response2.statusCode == HttpStatus.SC_OK
            verifyAll(fetchedUser) {
                createdUser.id == id
                createdUser.name == name
                createdUser.email == email
                createdUser.gender == gender
                createdUser.status == status
            }


        }

        where:
        name | email |gender|status|expected_code
        "John Doe"|"john.doe@gmail.com"|"male"|"active"|HttpStatus.SC_CREATED
        "John Doe"|"john.doe2@gmail.com"|"male"|"active"|HttpStatus.SC_CREATED //same name different email
        "Jane Doe"|"Jane.doe@gmail.com"|"female"|"active"|HttpStatus.SC_CREATED
        RandomGenerator.nameGenerator()|RandomGenerator.emailGenerator()|"female"|"active"|HttpStatus.SC_CREATED
        "Nalini Kaur"|"nalini_kaur@lang.name"|"male"|"active"|HttpStatus.SC_UNPROCESSABLE_ENTITY //user already exists
        ""|""|"female"|"active"|HttpStatus.SC_UNPROCESSABLE_ENTITY
        RandomGenerator.nameGenerator()|RandomGenerator.emailGenerator()|""|"active"|HttpStatus.SC_UNPROCESSABLE_ENTITY//missing data
        ""|RandomGenerator.emailGenerator()|"male"|"active"|HttpStatus.SC_UNPROCESSABLE_ENTITY//missing data
        RandomGenerator.nameGenerator()|RandomGenerator.emailGenerator()|""|"active"|HttpStatus.SC_UNPROCESSABLE_ENTITY//missing data
        RandomGenerator.nameGenerator()|RandomGenerator.emailGenerator()|"male"|""|HttpStatus.SC_UNPROCESSABLE_ENTITY//missing data

    }



    def "Get User"(int id, String name ,String email ,String gender,String status ,int expected_code)
    {
        when: "I get existing user"
        def response = Users.getUser(id)
        println("Get user")
        println(response.contentAsString )

        def fetchedUser = new JsonSlurper().parseText(response.contentAsString)


        then: "Validate Operation"
        response.statusCode == expected_code
        if(expected_code ==  HttpStatus.SC_OK) {
            verifyAll {
                fetchedUser.id == id
                fetchedUser.name == name
                fetchedUser.email == email
                fetchedUser.gender == gender
                fetchedUser.status == status
            }
        }
        where:
        id| name| email|gender|status|expected_code
        3651|"Chandramani Dhawan"|"dhawan_chandramani@steuber-swift.biz"|"female"|"inactive"|HttpStatus.SC_OK
        9999|""|""|""|""|HttpStatus.SC_NOT_FOUND

    }

    def "delete user"(){
        given: "I have existing users"

        def response = Users.getUsers()
        Assert.that(response.statusCode == HttpStatus.SC_OK)
        def userlist =  new JsonSlurper().parseText( response.contentAsString)

        and: "I select one to be deleted"
        def i = (Math.random()*363435) % userlist.size();
        def id4Delete = userlist[i].id
        when: "I Delete User"
        def deleteResponse = Users.deleteUser(id4Delete)


        then: "Validate Delete Operation"

        deleteResponse.statusCode ==  HttpStatus.SC_NO_CONTENT



    }


    def "update User"(int id, String name ,String email ,String gender,String status ,int expected_code) {
        given: "I have user parameters"
        def userData = [id: id, name: name, email: email, gender: gender, status: status]

        when: "Update User information"
        def response = Users.updateUser(userData)

        println("Updated user")
        println(response.contentAsString )
        def updatedUserData = new JsonSlurper().parseText(response.contentAsString)


        then: "Validate Operation"
        response.statusCode == expected_code

            verifyAll(updatedUserData) {
                userData.name == name
                userData.email == email
                userData.gender == gender
                userData.status == status
            }

        where:
        id| name | email |gender|status|expected_code
        13|"ev. Gopal Trivedi"|"trivedi_rev_gopal@mcglynn.org"|"male"|"inactive"|HttpStatus.SC_OK
        13|"ev. Gopal Trivedi"|RandomGenerator.emailGenerator()|"male"|"active"|HttpStatus.SC_OK
        13|RandomGenerator.nameGenerator()|"trivedi_rev_gopal@mcglynn.org"|"male"|"active"|HttpStatus.SC_OK


    }


}
