using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;

namespace ETUEtest
{
    static class ETUEReader
    {

        public static ETUEPackage parse(String url) 
        {
            using (XmlReader reader = XmlReader.Create(url))
            {
                ETUEPackage pack = new ETUEPackage();
                //Do some basic error checking, make sure this xml is 
                // of the right format
                reader.Read();
                if ( !reader.Name.Equals("ETUE-Config") )
                {
                    //If not print an error (TODO: May want to do more here)
                    Console.WriteLine("Error: Not an ETUE package file\n");
                    return pack;
                }

                //Continually read in and fill in the information about the package
                while (reader.Read())
                {
                    if( reader.NodeType == XmlNodeType.Element )
                    {
                        //Switch on the name of the element
                        switch (reader.Name)
                        {
                            //In each case, fill in the information as it will appear in a
                            // properly formatted ETUE-Config.xml 
                            // TODO: Error Checking??
                            case "UID" :
                                reader.Read();
                                pack.setUID(reader.Value);
                                break;
                            case "Title" :
                                reader.Read();
                                pack.setTitle(reader.Value);
                                break;
                            case "Description" :
                                reader.Read();
                                pack.setDescription(reader.Value);
                                break;
                            case "Basic" :
                                while (reader.NodeType != XmlNodeType.Text)
                                    reader.Read();
                                pack.setBasicScript(reader.Value.Trim());
                                break;
                            case "Advanced" :
                                while (reader.NodeType != XmlNodeType.Text)
                                    reader.Read();
                                pack.setAdvancedScript(reader.Value.Trim());
                                break;
                            case "Organization" :
                                reader.Read();
                                pack.setOrganization(reader.Value);
                                break;
                            case "Version-of-File" :
                                reader.Read();
                                pack.setFileVersion(reader.Value);
                                break;
                            case "ETUE-Version-Created-In" :
                                reader.Read();
                                pack.setVersionCreated(reader.Value);
                                break;
                        }
                    }
                }
                return pack;
            }
        }// parse



    }
}
