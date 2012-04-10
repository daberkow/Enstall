using System;
using System.Collections.Generic;
using System.Linq;
using System.IO;
using System.Web;
using System.Text;
using System.Net;

namespace EnstallClient
{
    class webUtil
    {
        public enum ResponseCategories
        {
            Unknown = 0,
            Informational = 1,
            Success = 2,
            Redirected = 3,
            ClientError = 4,
            ServerError = 5
        };

        public static HttpWebRequest GenerateHttpWebRequest(string uriString)
        {
            //Get a URI object
            Uri uri = new Uri(uriString);
            //Create the initial request
            HttpWebRequest httpRequest = (HttpWebRequest) HttpWebRequest.Create(uri);
            //Return the request
            return httpRequest;
        }

        public static HttpWebRequest GenerateHttpWebRequest(string uriString, string postData, string contentType)
        {
            //Get a URI object
            Uri uri = new Uri(uriString);
            //Create the initial request
            HttpWebRequest httpRequest = (HttpWebRequest)HttpWebRequest.Create(uri);

            //Get the bytes for the request 
            byte[] bytes = Encoding.UTF8.GetBytes(postData);

            //Set the content type of the data being posted
            httpRequest.ContentType = contentType;

            //Set the content length of the string being posted
            httpRequest.ContentLength = postData.Length;

            //Get the request stream and write the post data in
            using (Stream requestStream = httpRequest.GetRequestStream())
            {
                requestStream.Write(bytes, 0, bytes.Length);
            }

            //Return the request
            return httpRequest;
        }

        public static ResponseCategories VerifyResponse(HttpWebResponse httpResponse) 
        {
            int statusCode = (int)httpResponse.StatusCode;
            if(( statusCode >= 100) && (statusCode <= 199) ) 
            {
                return ResponseCategories.Informational;
            }
            else if(( statusCode >= 200) && (statusCode <= 299) ) 
            {
                return ResponseCategories.Success;   
            }
            else if(( statusCode >= 300) && (statusCode <= 399) )
            {
                return ResponseCategories.Redirected;
            }
            else if(( statusCode >= 400) && (statusCode <= 499) )
            {
                return ResponseCategories.ClientError;
            }
            else if(( statusCode >= 500) && (statusCode <= 599) )
            {
                return ResponseCategories.ServerError;
            }
            return ResponseCategories.Unknown;
        }

        public static string GetHtmlFromUrl(string url) 
        {
            if (string.IsNullOrEmpty(url)) 
                throw new ArgumentNullException("url", "Parameter is null or empty");

            string html = "";
            HttpWebRequest request = GenerateHttpWebRequest(url);
            using(HttpWebResponse response = (HttpWebResponse)request.GetResponse())
            {
                if (VerifyResponse(response) == ResponseCategories.Success )
                {
                    //Get the response stream
                    Stream responseStream = response.GetResponseStream();
                    //Use a stream reader that understands UTF8
                    using(StreamReader reader = new StreamReader(responseStream, Encoding.UTF8))
                    {
                        html = reader.ReadToEnd();
                    }
                }
            }

            //Return the html as a string
            return html;
        }
    }
}
