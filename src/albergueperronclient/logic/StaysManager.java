/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

import albergueperronclient.exceptions.BusinessLogicException;
import albergueperronclient.modelObjects.StayBean;
import java.util.Collection;

/**
 *
 * @author 2dam
 */
public interface StaysManager {
    public StayBean getStay(String id);
    
    public Collection<StayBean> getAllStays() throws BusinessLogicException;
    
    public void createStay(StayBean stay) throws BusinessLogicException;
    
    public void updateStay(StayBean stay) throws BusinessLogicException;
    
    public void deleteStay(String id) throws BusinessLogicException;
}
