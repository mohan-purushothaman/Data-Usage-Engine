/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.beans.impl;

import org.springframework.stereotype.Component;
import org.verizon.du.core.DataUsage;
import org.verizon.du.core.ExcludeFilter;

/**
 *
 * @author Administrator
 */
@Component
public class SimpleExcluder implements ExcludeFilter{

    @Override
    public boolean canExclude(DataUsage usage) {
        return true;
    }
    
}
