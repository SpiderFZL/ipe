package com.ipe.module.core.web.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ipe.module.core.entity.Resource;
import com.ipe.module.core.service.ResourceService;
import com.ipe.module.core.web.util.BodyWrapper;
import com.ipe.module.core.web.util.RestRequest;

/**
 * Created with IntelliJ IDEA.
 * Resource: tangdu
 * Date: 13-9-7
 * Time: 下午10:27
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/resource")
public class ResourceController extends AbstractController {

    @Autowired
    private ResourceService resourceService;

    @RequestMapping(value={"/list"})
    public @ResponseBody BodyWrapper list(Resource resource,RestRequest rest){
        try {
            resourceService.where(rest.getPageModel());
            return success(rest.getPageModel());
        }catch (Exception e){
            LOGGER.error("ERROR",e);
            return failure(e);
        }
    }


    @RequestMapping(value={"/edit"},method = RequestMethod.POST)
    public @ResponseBody BodyWrapper edit(Resource resource,RestRequest rest){
        try {
            Resource parent=resourceService.get(resource.getParent().getId());
            resource.setParent(parent);
            resource.setUpdatedDate(new Date());
            resourceService.update(resource);
            return success(resource);
        }catch (Exception e){
            LOGGER.error("ERROR",e);
            return failure(e);
        }
    }

    @RequestMapping(value={"/add"},method = RequestMethod.POST)
    public @ResponseBody BodyWrapper add(Resource resource,RestRequest rest){
        try {
            resourceService.saveResource(resource);
            return success(resource);
        }catch (Exception e){
            LOGGER.error("ERROR",e);
            return failure(e);
        }
    }

    @RequestMapping(value={"/del"})
    public @ResponseBody BodyWrapper del(String [] ids,RestRequest rest){
        try {
            resourceService.delete(ids);
            return success();
        }catch (Exception e){
            LOGGER.error("ERROR",e);
            return failure(e);
        }
    }

    @RequestMapping(value={"/getResources"})
    public @ResponseBody BodyWrapper getMenus(String pid){
        try {
        	Resource  data= resourceService.getTreeResources();
        	data.setLeaf(false);
            return success(data);
        }catch (Exception e){
            LOGGER.error("ERROR",e);
            return failure(e);
        }
    }
    
    /**
     * 更新排序及位置
     * @param ids
     * @param pid
     */
    @RequestMapping(value={"/updateSort"})
    public @ResponseBody BodyWrapper updateSort(String [] ids,String pid){
        try {
        	resourceService.updateSort(ids,pid);
            return success();
        }catch (Exception e){
            LOGGER.error("Exception {}",e);
            return failure(e);
        }
    }
}
