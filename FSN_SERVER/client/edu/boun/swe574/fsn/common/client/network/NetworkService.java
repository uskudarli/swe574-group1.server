
package edu.boun.swe574.fsn.common.client.network;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.1-b03-
 * Generated source version: 2.1
 * 
 */
@WebService(name = "NetworkService", targetNamespace = "http://ws.backend.fsn.swe574.boun.edu/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface NetworkService {


    /**
     * 
     * @param token
     * @return
     *     returns edu.boun.swe574.fsn.common.client.network.GetProfileResponse
     */
    @WebMethod
    @WebResult(partName = "return")
    public GetProfileResponse getProfileOfSelf(
        @WebParam(name = "token", partName = "token")
        String token);

    /**
     * 
     * @param dateOfBirth
     * @param location
     * @param token
     * @param profileMessage
     * @return
     *     returns edu.boun.swe574.fsn.common.client.network.BaseServiceResponse
     */
    @WebMethod
    @WebResult(partName = "return")
    public BaseServiceResponse editProfile(
        @WebParam(name = "token", partName = "token")
        String token,
        @WebParam(name = "location", partName = "location")
        String location,
        @WebParam(name = "dateOfBirth", partName = "dateOfBirth")
        String dateOfBirth,
        @WebParam(name = "profileMessage", partName = "profileMessage")
        String profileMessage);

    /**
     * 
     * @param queryString
     * @param token
     * @return
     *     returns edu.boun.swe574.fsn.common.client.network.SearchForUsersResponse
     */
    @WebMethod
    @WebResult(partName = "return")
    public SearchForUsersResponse searchForUsers(
        @WebParam(name = "token", partName = "token")
        String token,
        @WebParam(name = "queryString", partName = "queryString")
        String queryString);

    /**
     * 
     * @param email
     * @param token
     * @return
     *     returns edu.boun.swe574.fsn.common.client.network.GetProfileResponse
     */
    @WebMethod
    @WebResult(partName = "return")
    public GetProfileResponse getProfileOfOtherUser(
        @WebParam(name = "token", partName = "token")
        String token,
        @WebParam(name = "email", partName = "email")
        String email);

    /**
     * 
     * @param email
     * @param token
     * @return
     *     returns edu.boun.swe574.fsn.common.client.network.BaseServiceResponse
     */
    @WebMethod
    @WebResult(partName = "return")
    public BaseServiceResponse follow(
        @WebParam(name = "token", partName = "token")
        String token,
        @WebParam(name = "email", partName = "email")
        String email);

    /**
     * 
     * @param token
     * @return
     *     returns edu.boun.swe574.fsn.common.client.network.SearchForUsersResponse
     */
    @WebMethod
    @WebResult(partName = "return")
    public SearchForUsersResponse getFollowedUser(
        @WebParam(name = "token", partName = "token")
        String token);

    /**
     * 
     * @param index
     * @param token
     * @param pageSize
     * @return
     *     returns edu.boun.swe574.fsn.common.client.network.GetRecipeFeedsResponse
     */
    @WebMethod
    @WebResult(partName = "return")
    public GetRecipeFeedsResponse getRecipeFeeds(
        @WebParam(name = "token", partName = "token")
        String token,
        @WebParam(name = "index", partName = "index")
        int index,
        @WebParam(name = "pageSize", partName = "pageSize")
        int pageSize);

}