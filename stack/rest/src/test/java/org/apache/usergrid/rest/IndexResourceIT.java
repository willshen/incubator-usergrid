/*
 *
 *  * Licensed to the Apache Software Foundation (ASF) under one or more
 *  *  contributor license agreements.  The ASF licenses this file to You
 *  * under the Apache License, Version 2.0 (the "License"); you may not
 *  * use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.  For additional information regarding
 *  * copyright in this work, please see the NOTICE file in the top level
 *  * directory of this distribution.
 *
 */

package org.apache.usergrid.rest;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.usergrid.rest.test.resource2point0.AbstractRestIT;
import org.apache.usergrid.rest.test.resource2point0.model.*;
import org.apache.usergrid.rest.test.resource2point0.model.ApiResponse;

import static org.junit.Assert.assertNotNull;


/**
 * test index creation
 */
public class IndexResourceIT extends AbstractRestIT {


//    @Rule
//    public TestContextSetup context = new TestContextSetup( this );
    //Used for all MUUserResourceITTests
    private Logger LOG = LoggerFactory.getLogger(IndexResourceIT.class);

    public IndexResourceIT(){

    }

//    @Ignore( "will finish when tests are working from rest" )
//    @Test
//    public void TestAddIndex() throws Exception{
//
//        String superToken = superAdminToken();
//
//        Map<String, Object> data = new HashMap<String, Object>();
//        data.put( "replicas", 0 );
//        data.put( "shards", 1 );
//        data.put( "writeConsistency", "one" );
//
//        UUID appId = this.context.getAppUuid();
//
//        // change the password as admin. The old password isn't required
//        JsonNode node = null;
//        try {
//            node = mapper.readTree(resource().path("/system/index/" + appId)
//                    .queryParam("access_token", superToken)
//                    .accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON_TYPE)
//                    .post(String.class, data));
//        } catch (Exception e) {
//            LOG.error("failed", e);
//            fail(e.toString());
//        }
//        assertNull( getError( node ) );
//
//        refreshIndex("test-organization", "test-app");
//
//    }


    /**
     * checks that reindex endpoint is case insensitive
     */
    @Test
    public void testCaseReindexEndpoint() {

        //Create Collection
        Map payload = new HashMap<>(  );
        payload.put( "name","wazer wifle!!" );
        ApiResponse collectionResponse = clientSetup.getRestClient()
            .pathResource( getOrgAppPath( "belp" ),this.getAdminToken() ).post( payload );

        assertNotNull( collectionResponse );

        //try reindex endpoint with ALl mixed case characters
        Token superUserToken = clientSetup.getRestClient().management().token().get(clientSetup.getSuperuserName(),clientSetup.getSuperuserPassword());

        QueryParameters queryParameters = new QueryParameters();
        //queryParameters.addParam( "access_token",this.clientSetup.getSuperuserToken().getAccessToken());
        ApiResponse result = clientSetup.getRestClient()
                                   .pathResource( "system/index/rebuild/"+clientSetup.getAppUuid()+"/bElp")
            .post(ApiResponse.class,true);
                                  // .post( false, ApiResponse.class, null, queryParameters, true );

        assertNotNull(result);

        //try the reindex endpoint with all lowercase Characters
        queryParameters = new QueryParameters();
       // queryParameters.addParam( "access_token",clientSetup.getSuperuserToken().getAccessToken() );
        result = clientSetup.getRestClient()
                                        .pathResource( "system/index/rebuild/"+clientSetup.getAppUuid()+"/belp")
                            .post(ApiResponse.class,true );
        assertNotNull( result );

    }
}
