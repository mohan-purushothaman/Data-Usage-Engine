/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.core;

/**
 *
 * @author Administrator
 */
public interface Aggregator {
    Customer aggregate(DataUsage usage);
}
