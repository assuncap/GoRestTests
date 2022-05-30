package org.example.gorest.framework


class Users {
    static def getUsers(){
       return RequestHelper.getRequest('users/')
    }

    static def createUser(Map user)
    {
       return RequestHelper.postRequest('users/', user)

    }

    static def updateUser(Map user)
    {
        return RequestHelper.putRequest('users/'+user.id, user)

    }

    static def getUser(int  id){
        return RequestHelper.getRequest('users/'+id)
    }

    static def  deleteUser(int id) {
        return RequestHelper.deleteRequest('users/'+id)
    }
}
