/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright @2010 bemoko
 */


/*
 * Plugin to generate gravatar URLs to retrieve profile images
 * 
 */

package gravatar

import java.net.URLEncoder;
import java.security.MessageDigest
import com.bemoko.live.platform.mwc.plugins.AbstractPlugin

class Gravatar extends AbstractPlugin {
    private def url='http://www.gravatar.com/avatar/'
    private def defaultUrl
    private def rating
    private def defaultImageSize
    
    void initialise(Map parameters) {
        defaultUrl=parameters?.defaultImageUrl
        rating=parameters?.rating 
        defaultImageSize=parameters?.defaultImageSize ?: 80 //80 is the gravatar default
        
    }
    
    String getImageUrl(String email) { return getImageUrl(email,defaultImageSize,defaultUrl)
    }
    String getImageUrl(String email,int imageSize) { return getImageUrl(email,imageSize,defaultUrl)
    }
    String getImageUrl(String email,String defaultImageUrl) { return getImageUrl(email,defaultImageSize,defaultImageUrl)
    }
    
    String getImageUrl(String email, int imageSize, String defaultImageUrl){
        return url+md5(email)+"?s=${imageSize}"+(rating ? "&r=${rating}" : '')+(defaultImageUrl ? "&d="+encode(defaultImageUrl): '')
    }
    
    private String md5(String email) {
        MessageDigest digest = MessageDigest.getInstance("MD5")
        def digestBytes=digest.digest(email.bytes)
        // Create Hex String  
        StringBuffer hexString = new StringBuffer();  
        for (int i=0; i<digestBytes.length; i++)  {
            hexString.append(Integer.toHexString(0xFF & digestBytes[i]).padLeft(2,"0"));  
    }
        return hexString.toString();  
    }
    
    private def encode(String url){
        return URLEncoder.encode(url)
    }   
}