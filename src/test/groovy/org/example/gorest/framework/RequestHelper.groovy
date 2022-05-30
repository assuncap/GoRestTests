package org.example.gorest.framework

import groovy.json.JsonOutput
import org.example.gorest.framework.Settings
import wslite.http.HTTPClientException
import wslite.rest.RESTClient
import wslite.rest.Response

class RequestHelper {

 static def getRequest(String path)
    {
        def client = new RESTClient(Settings.BASE_URL)
        try {
        return client.get( path:path,
                                            headers: [authorization:Settings.AUTHORIZATION,'Content-Type': 'application/json; charset=UTF-8', 'Accept': 'application/json'])
        }
        catch (HTTPClientException httpEx) { //handling exceptions raised by the framework when status code is not 2XX
            return new Response(httpEx.getRequest(), httpEx.getResponse())


        }

    }

    static def deleteRequest(String path)
    {
        def client = new RESTClient(Settings.BASE_URL)
        try {
            return client.delete( path:path,
                    headers: [authorization:Settings.AUTHORIZATION,'Content-Type': 'application/json; charset=UTF-8', 'Accept': 'application/json'])
        }
        catch (HTTPClientException httpEx) { //handling exceptions raised by the framework when status code is not 2XX
            return new Response(httpEx.getRequest(), httpEx.getResponse())


        }

    }





    static postRequest(String path, Map requestBody) {
        def client = new RESTClient(Settings.BASE_URL)

        try {
            return client.post(path: path,
                    headers: [authorization: Settings.AUTHORIZATION, 'Content-Type': 'application/json; charset=UTF-8', 'Accept': 'application/json'])
            {
                text JsonOutput.toJson(requestBody)
            }
        }
        catch (HTTPClientException httpEx) { //handling exceptions raised by the framework when status code is not 2XX
            return new Response(httpEx.getRequest(), httpEx.getResponse())


        }
    }


    static putRequest(String path, Map requestBody) {
        def client = new RESTClient(Settings.BASE_URL)

        try {
            return client.put(path: path,
                    headers: [authorization: Settings.AUTHORIZATION, 'Content-Type': 'application/json; charset=UTF-8', 'Accept': 'application/json'])
            {
                text JsonOutput.toJson(requestBody)
            }
        }
        catch (HTTPClientException httpEx) { //handling exceptions raised by the framework when status code is not 2XX
            return new Response(httpEx.getRequest(), httpEx.getResponse())


        }
    }


}
