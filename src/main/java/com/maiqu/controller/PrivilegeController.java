package com.maiqu.controller;

import com.maiqu.domain.model.Privilege;
import com.maiqu.domain.model.Role;
import com.maiqu.domain.request.PrivilegeVo;
import com.maiqu.domain.request.dto.PageDto;
import com.maiqu.domain.response.BaseResponse;
import com.maiqu.domain.response.Page;
import com.maiqu.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api("权限管理接口")
@RestController
@RequestMapping("/admin/auth")
public class PrivilegeController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取角色列表", notes = "获取角色列表")
    @GetMapping("/role/list")
    public BaseResponse<List<Role>> roleList(){
        return BaseResponse.success(roleService.getRoleList());
    }


    @ApiOperation(value = "获取用户对应的角色", notes = "获取角色列表")
    @GetMapping("/get/role")
    public BaseResponse<List<Role>> roleListByUserId(Integer userId){
        return BaseResponse.success(roleService.getRoleListByUserId(userId));
    }

    @ApiOperation(value = "角色列表", notes = "角色列表")
    @PostMapping("/role/list")
    public BaseResponse<Page> roleList(@RequestBody PageDto pageDto){
        return roleService.roleList(pageDto);
    }

    @ApiOperation(value = "增加角色", notes = "增加角色")
    @PostMapping("/add/role")
    public BaseResponse<Boolean> addRole(@RequestBody Role role){
        return BaseResponse.success(roleService.addRole(role));
    }

    @ApiOperation(value = "编辑角色", notes = "编辑角色")
    @PostMapping("/edit/role")
    public BaseResponse<Boolean> editRole(@RequestBody Role role){
        return BaseResponse.success(roleService.editRole(role));
    }

    @ApiOperation(value = "删除角色", notes = "删除角色")
    @GetMapping("/delete/role")
    public BaseResponse<Boolean> deleteRole(Integer roleId){
        return BaseResponse.success(roleService.deleteRole(roleId));
    }

    @ApiOperation(value = "绑定角色", notes = "绑定角色")
    @GetMapping("/band/role")
    public BaseResponse<Boolean> bandRole(Integer userId, Integer roleId){
        return roleService.bandUserRole(userId,roleId);
    }

    @ApiOperation(value = "解除绑定角色", notes = "解除绑定角色")
    @GetMapping("/unband/role")
    public BaseResponse<Boolean> unbandRole(Integer userId, Integer roleId){
        return roleService.unBandUserRole(userId,roleId);
    }

    @ApiOperation(value = "权限列表", notes = "权限列表")
    @PostMapping("/privilege/list")
    public BaseResponse<Page> privilegeList(@RequestBody PageDto pageDto){
        return roleService.privilegeList(pageDto);
    }

    @ApiOperation(value = "增加权限", notes = "增加权限")
    @PostMapping("/add/privilege")
    public BaseResponse<Boolean> addPrivilege(@RequestBody  Privilege privilege){
        return BaseResponse.success(roleService.addPrivilege(privilege));
    }

    @ApiOperation(value = "编辑权限", notes = "编辑权限")
    @PostMapping("/edit/privilege")
    public BaseResponse<Boolean> editPrivilege(@RequestBody Privilege privilege){
        return BaseResponse.success(roleService.editPrivilege(privilege));
    }

    @ApiOperation(value = "删除权限", notes = "删除权限")
    @GetMapping("/delete/privilege")
    public BaseResponse<Boolean> deletePrivilege(Integer privilegeId){
        return BaseResponse.success(roleService.deletePrivilege(privilegeId));
    }

    @ApiOperation(value = "绑定权限", notes = "绑定权限")
    @PostMapping("/band/privilege")
    public BaseResponse<Boolean> bandPrivilege(@RequestBody PrivilegeVo privilegeVo){
        return roleService.bandRolePrivilege(privilegeVo);
    }

    @ApiOperation(value = "解除绑定权限", notes = "解除绑定权限")
    @PostMapping("/unband/privilege")
    public BaseResponse<Boolean> unbandPrivilege(@RequestBody PrivilegeVo privilegeVo){
        return roleService.unbandRolePrivilege(privilegeVo);
    }

    @ApiOperation(value = "获取角色对应的权限", notes = "获取权限列表")
    @GetMapping("/get/privilege")
    public BaseResponse<List<Privilege>> getPrivilegeByRoleId(Integer roleId){
        return BaseResponse.success(roleService.getPrivilegeListByRoleId(roleId));
    }

    @ApiOperation(value = "获取全部权限列表", notes = "获取全部权限列表")
    @GetMapping("/privilege/list")
    public BaseResponse<List<Privilege>> getPrivilegeList(){
        return BaseResponse.success(roleService.getPrivilegeList());
    }

    @ApiOperation(value = "获取角色下的权限树", notes = "获取全部权限列表")
    @GetMapping("/privilege/tree")
    public BaseResponse<List<Privilege>> getPrivilegeTree(){
        return BaseResponse.success(roleService.getPrivilegeTree());
    }

}
