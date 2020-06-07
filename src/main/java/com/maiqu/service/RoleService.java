package com.maiqu.service;

import com.maiqu.domain.model.Privilege;
import com.maiqu.domain.model.Role;
import com.maiqu.domain.model.RolePrivilege;
import com.maiqu.domain.model.User;
import com.maiqu.domain.request.PrivilegeVo;
import com.maiqu.domain.request.dto.PageDto;
import com.maiqu.domain.response.*;
import com.maiqu.filter.LoginFilter;
import com.maiqu.mapper.PrivilegeMapper;
import com.maiqu.mapper.RoleMapper;
import com.maiqu.mapper.RolePrivilegeMapper;
import com.maiqu.mapper.UserRoleMapper;
import com.maiqu.util.CommonCode;
import io.netty.util.internal.StringUtil;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PrivilegeMapper privilegeMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RolePrivilegeMapper rolePrivilegeMapper;

    /**
     * 获取角色列表
     * @return
     */
    public List<Role> getRoleList(){
        return roleMapper.getRoles();
    }

    /**
     * 获取用户指定的角色
     * @param userId
     * @return
     */
    public List<Role> getRoleListByUserId(Integer userId){
        return roleMapper.getRolesByUserId(userId);
    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    public BaseResponse<Boolean> addRole(Role role){
        if(role == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"参数不合法");
        }
        if(StringUtils.isEmpty(role.getRoleName())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"角色名称为空");
        }

         if(roleMapper.getRoleByName(role.getRoleName())!=null){
             return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"已存在此角色名称");
         }

        roleMapper.insertRole(role);
        return BaseResponse.success(true);
    }


    /**
     * 获取角色列表
     * @param pageDto
     * @return
     */
    public BaseResponse<Page> roleList(PageDto pageDto){
        List<Role> roles = roleMapper.findRoleList(pageDto);
        if(roles == null && roles.size() == 0){
            return BaseResponse.success(Page.fail());
        }
        Integer total = roleMapper.findRoleListCount();
        Integer pageNumber = pageDto.getPageNumber(total);
        return BaseResponse.success(Page.success(total,pageNumber,roles));
    }

    /**
     * 获取权限列表
     * @param pageDto
     * @return
     */
    public BaseResponse<Page> privilegeList(PageDto pageDto){
        List<Privilege> privileges = privilegeMapper.findPrivilegeList(pageDto);
        if(privileges == null && privileges.size() == 0){
            return BaseResponse.success(Page.fail());
        }
        Integer total = privilegeMapper.findPrivilegeListCount();
        Integer pageNumber = pageDto.getPageNumber(total);
        return BaseResponse.success(Page.success(total,pageNumber,privileges));
    }



    /**
     * 编辑角色
     * @param role
     * @return
     */
    public BaseResponse<Boolean> editRole(Role role){
        if(role == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"参数不合法");
        }
        if(StringUtils.isEmpty(role.getRoleName())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"角色名称为空");
        }

        if(roleMapper.getRoleByName(role.getRoleName())!=null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"已存在此角色名称");
        }

        roleMapper.updateRole(role);
        return BaseResponse.success(true);
    }

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    public BaseResponse<Boolean> deleteRole(Integer roleId){
        if(roleId == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"角色ID为空");
        }
        Role role = roleMapper.getRoleById(roleId);
        if(role == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"删除失败,不存在该角色ID");
        }
        roleMapper.deleteRole(roleId);
        return BaseResponse.success(true);
    }

    /**
     * 删除权限
     * @param privilegeId
     * @return
     */
    public BaseResponse<Boolean> deletePrivilege(Integer privilegeId){
        if(privilegeId == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"权限ID为空");
        }
        Privilege privilege = privilegeMapper.getPrivilegeById(privilegeId);
        if(privilege == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"删除失败,不存在该权限ID");
        }
        privilegeMapper.deletePrivilege(privilegeId);
        return BaseResponse.success(true);
    }


    /**
     * 添加权限
     * @param privilege
     * @return
     */
    public BaseResponse<Boolean> addPrivilege(Privilege privilege){
        if(privilege == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"参数不合法");
        }
        if(StringUtils.isEmpty(privilege.getPrivilegeName())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"权限名称为空");
        }

        if(StringUtils.isEmpty(privilege.getPath())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"权限路径为空");
        }

        if(privilege.getLevel()==null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"权限等级为空");
        }

        if(privilegeMapper.getPrivilegeByName(privilege.getPrivilegeName())!=null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"已存在此角色名称");
        }

        privilegeMapper.insertPrivilege(privilege);
        return BaseResponse.success(true);
    }

    /**
     * 编辑权限
     * @param privilege
     * @return
     */
    public BaseResponse<Boolean> editPrivilege(Privilege privilege){
        if(privilege == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"参数不合法");
        }
        if(StringUtils.isEmpty(privilege.getPrivilegeName())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"权限名称为空");
        }

        if(StringUtils.isEmpty(privilege.getPath())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"权限路径为空");
        }

        if(privilege.getLevel()==null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"权限等级为空");
        }

        if(privilegeMapper.getPrivilegeByName(privilege.getPrivilegeName())!=null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"已存在此角色名称");
        }

        privilegeMapper.updatePrivilege(privilege);
        return BaseResponse.success(true);
    }

    /**
     * 获取角色指定的权限
     * @param roleId
     * @return
     */
    public List<Privilege> getPrivilegeListByRoleId(Integer roleId){
        return privilegeMapper.getPrivilegeByRoleId(roleId);
    }

    /**
     * 获取三级权限列表
     * @return
     */
    public List<Privilege> getPrivilegeList(){
        return privilegeMapper.getPrivilegeList();
    }


    /**
     * 获取用户对应的权限树
     * @param flag  true:表示获取用户所属的权限 false:获取全部权限
     * @return
     */
    public BaseResponse<PrivilegeTree> getPrivilegeTree(Boolean flag){
        User user = LoginFilter.getUser();
        if(user == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"查询不到用户信息");
        }
        System.out.print("用户信息："+user.toString());
        List<Role> roles = getRoleListByUserId(user.getId());   //获取角色信息
        if(roles==null || roles.size() == 0){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"该用户还未分配角色");
        }
        List<Integer> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());
        List<Privilege> privileges = getPrivilegeList();        //获取一颗权限树
        if(privileges == null && privileges.size() == 0){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"还没录入权限信息，请录入信息");
        }
        Map<Integer,List<RolePrivilege>> userPrivilegeMap = rolePrivilegeMapper.getPrivilegesByRoleId(roleIds).stream().collect(Collectors.groupingBy(RolePrivilege::getRoleId,
                Collectors.mapping(rolePrivilege -> rolePrivilege, Collectors.toList())));


        PrivilegeTree privilegeTree = new PrivilegeTree();
        for(Role role:roles){
            if(userPrivilegeMap.containsKey(role.getId())){
                 List<RolePrivilege> rolePrivileges = userPrivilegeMap.get(role.getId());
                List<Integer> privilegeIds = new ArrayList<>();
                if(rolePrivileges != null && rolePrivileges.size() > 0){
                    privilegeIds = rolePrivileges.stream().map(RolePrivilege::getPrivilegeId).collect(Collectors.toList());
                }
                privileges = setPrivilege(privileges,privilegeIds);
            }
            if(flag){
                privileges = handlePrivilege(privileges);
            }
            role.setSelect(true);
            role.setPrivileges(privileges);
        }
        privilegeTree.setRoles(roles);

        return BaseResponse.success(privilegeTree);
    }

    public List<Privilege> setPrivilege(List<Privilege> privileges, List<Integer> privilegeIds){
        for(Privilege privilege : privileges){
            if(privilegeIds.contains(privilege.getId())){
                privilege.setSelect(true);
            }
            if(privilege.getPrivileges()!=null && privilege.getPrivileges().size()>0){
                setPrivilege(privilege.getPrivileges(),privilegeIds);
            }
        }
        return privileges;
    }



    /**
     * 过滤用户选中权限
     * @param privileges
     * @return
     */
    public List<Privilege> handlePrivilege(List<Privilege> privileges){
        if(privileges!=null && privileges.size()>0) {
            for (Privilege p : privileges) {
                List<Privilege> childs = p.getPrivileges();
                if (childs != null && childs.size() > 0) {
                    childs = childs.stream().filter(child -> child.isSelect()).collect(Collectors.toList());
                }
                p.setPrivileges(childs);
                handlePrivilege(childs);
            }
        }
        return privileges;
    }

    /**
     * 绑定角色
     * @param userId
     * @param roleId
     * @return
     */
    public BaseResponse<Boolean> bandUserRole(Integer userId, Integer roleId){
        userRoleMapper.deleteRole(userId);
        userRoleMapper.bandRole(userId,roleId);
        return BaseResponse.success("绑定角色成功");
    }

    /**
     * 绑定角色
     * @param userId
     * @param roleId
     * @return
     */
    public BaseResponse<Boolean> unBandUserRole(Integer userId, Integer roleId){
        userRoleMapper.unbandRole(userId,roleId);
        return BaseResponse.success("解绑角色成功");
    }

    /**
     * 绑定权限
     * @param privilegeVo
     * @return
     */
    public BaseResponse<Boolean> bandRolePrivilege(PrivilegeVo privilegeVo){
        if(privilegeVo == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR, "没有参数传入");
        }
        if(privilegeVo.getRoleId() == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR, "没有传入roleId");
        }
        if(privilegeVo.getPrivilegeIds() == null || privilegeVo.getPrivilegeIds().size() == 0){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR, "没有传入privilegeIds数组");
        }
        rolePrivilegeMapper.bandPrivilege(privilegeVo);
        return BaseResponse.success("绑定权限成功");
    }

    /**
     * 解绑权限
     * @param privilegeVo
     * @return
     */
    public BaseResponse<Boolean> unbandRolePrivilege(PrivilegeVo privilegeVo){
        if(privilegeVo == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR, "没有参数传入");
        }
        if(privilegeVo.getRoleId() == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR, "没有传入roleId");
        }
        if(privilegeVo.getPrivilegeIds() == null || privilegeVo.getPrivilegeIds().size() == 0){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR, "没有传入privilegeIds数组");
        }
        rolePrivilegeMapper.unbandPrivilege(privilegeVo);
        return BaseResponse.success("解绑权限成功");
    }
}
