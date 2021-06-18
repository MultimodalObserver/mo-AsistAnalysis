/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Lathy
 */
public class CloudTags {
    
    private String word;
    private int length;
    private int frequency;
    
    public CloudTags() {
    }

    public CloudTags(String word, int length, int frequency) {
        this.word = word;
        this.length = length;
        this.frequency = frequency;
    }
    
    

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
    
    
    
    
}
