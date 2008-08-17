/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.pdfbox.pdmodel;

import org.pdfbox.cos.COSBase;
import org.pdfbox.cos.COSDictionary;
import org.pdfbox.pdmodel.common.COSObjectable;

/**
 * This class holds all of the name trees that are available at the document level.
 *
 * @author <a href="mailto:ben@benlitchfield.com">Ben Litchfield</a>
 * @version $Revision: 1.4 $
 */
public class PDDocumentNameDictionary implements COSObjectable
{
    private COSDictionary nameDictionary;
    private PDDocumentCatalog catalog;

    /**
     * Constructor.
     *
     * @param cat The document catalog that this dictionary is part of.
     */
    public PDDocumentNameDictionary( PDDocumentCatalog cat )
    {
        nameDictionary = new COSDictionary();
        catalog = cat;
    }

    /**
     * Constructor.
     *
     * @param cat The document that this dictionary is part of.
     * @param names The names dictionary.
     */
    public PDDocumentNameDictionary( PDDocumentCatalog cat, COSDictionary names )
    {
        catalog = cat;
        nameDictionary = names;
    }

    /**
     * Convert this standard java object to a COS object.
     *
     * @return The cos object that matches this Java object.
     */
    public COSBase getCOSObject()
    {
        return nameDictionary;
    }

    /**
     * Convert this standard java object to a COS object.
     *
     * @return The cos dictionary for this object.
     */
    public COSDictionary getCOSDictionary()
    {
        return nameDictionary;
    }

    /**
     * Get the destination named tree node.  The value in this name tree will be PDDestination
     * objects.
     *
     * @return The destination name tree node.
     */
    public PDDestinationNameTreeNode getDests()
    {
        PDDestinationNameTreeNode dests = null;

        COSDictionary dic = (COSDictionary)nameDictionary.getDictionaryObject( "Dests" );

        //The document catalog also contains the Dests entry sometimes
        //so check there as well.
        if( dic == null )
        {
            dic = (COSDictionary)catalog.getCOSDictionary().getDictionaryObject( "Dests" );
        }

        if( dic != null )
        {
            dests = new PDDestinationNameTreeNode( dic );
        }


        return dests;
    }

    /**
     * Set the named destinations that are associated with this document.
     *
     * @param dests The destination names.
     */
    public void setDests( PDDestinationNameTreeNode dests )
    {
        nameDictionary.setItem( "Dests", dests );
        //The dests can either be in the document catalog or in the
        //names dictionary, PDFBox will just maintain the one in the
        //names dictionary for now unless there is a reason to do
        //something else.
        //clear the potentially out of date Dests reference.
        catalog.getCOSDictionary().setItem( "Dests", (COSObjectable)null);
    }

    /**
     * Get the embedded files named tree node.  The value in this name tree will be PDComplexFileSpecification
     * objects.
     *
     * @return The embedded files name tree node.
     */
    public PDEmbeddedFilesNameTreeNode getEmbeddedFiles()
    {
        PDEmbeddedFilesNameTreeNode retval = null;

        COSDictionary dic = (COSDictionary)nameDictionary.getDictionaryObject( "EmbeddedFiles" );

        if( dic != null )
        {
            retval = new PDEmbeddedFilesNameTreeNode( dic );
        }

        return retval;
    }

    /**
     * Set the named embedded files that are associated with this document.
     *
     * @param ef The new embedded files
     */
    public void setEmbeddedFiles( PDEmbeddedFilesNameTreeNode ef )
    {
        nameDictionary.setItem( "EmbeddedFiles", ef );
    }

    /**
     * Get the document level javascript entries.  The value in this name tree will be PDTextStream.
     *
     * @return The document level named javascript.
     */
    public PDJavascriptNameTreeNode getJavaScript()
    {
        PDJavascriptNameTreeNode retval = null;

        COSDictionary dic = (COSDictionary)nameDictionary.getDictionaryObject( "JavaScript" );

        if( dic != null )
        {
            retval = new PDJavascriptNameTreeNode( dic );
        }

        return retval;
    }

    /**
     * Set the named javascript entries that are associated with this document.
     *
     * @param js The new Javascript entries.
     */
    public void setJavascript( PDJavascriptNameTreeNode js )
    {
        nameDictionary.setItem( "JavaScript", js );
    }
}