package com.example.stefano.lomux_pro.model;

import java.io.Serializable;

/**
 * Created by Stefano on 18/10/2017.
 */

    public class Link implements Serializable {
        private String value;
        private String href;

        public Link(String text, String href)
        {
            value = text;
            this.href = href;
        }

        public String getText(){
            return value;
        }

        public String getUri(){
            return href;
        }


    }
