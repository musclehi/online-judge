package cn.idealismxxm.onlinejudge.web.controller;

import cn.idealismxxm.onlinejudge.domain.annotation.RequirePrivilege;
import cn.idealismxxm.onlinejudge.domain.entity.*;
import cn.idealismxxm.onlinejudge.domain.enums.CommonConstant;
import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.enums.PrivilegeEnum;
import cn.idealismxxm.onlinejudge.domain.util.*;
import cn.idealismxxm.onlinejudge.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 标签相关操作
 *
 * @author idealism
 * @date 2018/6/3
 */
@Controller
@RequestMapping("tag")
public class TagController {
    @Resource
    private TagService tagService;

    /**
     * 添加标签
     *
     * @param tagJson 标签 json
     * @return 标签的id
     */
    @RequirePrivilege(privilegeEnum = {PrivilegeEnum.SIGN_IN, PrivilegeEnum.MANAGE_TAG})
    @ResponseBody
    @RequestMapping(value = "addTag", method = {RequestMethod.POST})
    public AjaxResult<Integer> addTag(String tagJson) {
        Tag tag = JsonUtil.jsonToObject(tagJson, Tag.class);
        User user = RequestUtil.getAttribute(CommonConstant.SESSION_ATTRIBUTE_USER);
        Integer id = tagService.addTag(tag, user.getUsername());
        return new AjaxResult<>(ErrorCodeEnum.SUCCESS.getMsg(), id);
    }

    /**
     * 编辑标签
     *
     * @param tagJson 标签 json
     * @return true / false
     */
    @RequirePrivilege(privilegeEnum = {PrivilegeEnum.SIGN_IN, PrivilegeEnum.MANAGE_TAG})
    @ResponseBody
    @RequestMapping(value = "editTag", method = {RequestMethod.POST})
    public AjaxResult<Boolean> editTag(String tagJson) {
        Tag tag = JsonUtil.jsonToObject(tagJson, Tag.class);
        User user = RequestUtil.getAttribute(CommonConstant.SESSION_ATTRIBUTE_USER);
        Boolean result = tagService.editTag(tag, user.getUsername());
        return new AjaxResult<>(ErrorCodeEnum.SUCCESS.getMsg(), result);
    }

    /**
     * 删除标签
     *
     * @param tagId 标签主键
     * @return true / false
     */
    @RequirePrivilege(privilegeEnum = {PrivilegeEnum.SIGN_IN, PrivilegeEnum.MANAGE_TAG})
    @ResponseBody
    @RequestMapping(value = "deleteTag", method = {RequestMethod.POST})
    public AjaxResult<Boolean> deleteTag(Integer tagId) {
        User user = RequestUtil.getAttribute(CommonConstant.SESSION_ATTRIBUTE_USER);
        Boolean result = tagService.deleteTag(tagId, user.getUsername());
        return new AjaxResult<>(ErrorCodeEnum.SUCCESS.getMsg(), result);
    }

    /**
     * 分页获取标签列表
     *
     * @param queryParamJson 查询条件 的 json串
     * @return 标签列表分页封装
     */
    @RequirePrivilege
    @ResponseBody
    @RequestMapping(value = "pageTag", method = {RequestMethod.GET})
    public AjaxResult<Pagination<Tag>> pageTag(String queryParamJson) {
        QueryParam queryParam = JsonUtil.jsonToObject(queryParamJson, QueryParam.class);
        Pagination<Tag> result = tagService.pageTagByQueryParam(queryParam);
        return new AjaxResult<>(ErrorCodeEnum.SUCCESS.getMsg(), result);
    }

    /**
     * 获取标签
     *
     * @param tagId 标签 主键
     * @return 标签的id
     */
    @RequirePrivilege(privilegeEnum = {PrivilegeEnum.SIGN_IN, PrivilegeEnum.MANAGE_TAG})
    @ResponseBody
    @RequestMapping(value = "getTag", method = {RequestMethod.GET})
    public AjaxResult<Tag> getTag(Integer tagId) {
        return new AjaxResult<>(ErrorCodeEnum.SUCCESS.getMsg(), tagService.getTagById(tagId));
    }
}
