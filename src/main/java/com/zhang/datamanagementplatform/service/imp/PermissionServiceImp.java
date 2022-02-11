package com.zhang.datamanagementplatform.service.imp;

import com.zhang.datamanagementplatform.entity.DTO.Permission;
import com.zhang.datamanagementplatform.entity.DTO.Role;
import com.zhang.datamanagementplatform.entity.POJO.PO.RolePermissionPO;
import com.zhang.datamanagementplatform.entity.POJO.PO.UserPO;
import com.zhang.datamanagementplatform.entity.POJO.PO.UserRolePO;
import com.zhang.datamanagementplatform.entity.POJO.VO.ResultVO;
import com.zhang.datamanagementplatform.entity.POJO.VO.RolePermissionVO;
import com.zhang.datamanagementplatform.entity.POJO.VO.UserRoleVO;
import com.zhang.datamanagementplatform.service.PermissionService;
import com.zhang.datamanagementplatform.constants.PermissionActionConstant;
import com.zhang.datamanagementplatform.constants.ResourceConstants;
import com.zhang.datamanagementplatform.dao.PermissionDao;
import com.zhang.datamanagementplatform.dao.UserDao;
import com.zhang.datamanagementplatform.enums.ResultEnum;
import com.zhang.datamanagementplatform.util.ObjectTransformUtil;
import com.zhang.datamanagementplatform.util.RedisUtil;
import com.zhang.datamanagementplatform.util.ResultUtil;
import com.zhang.datamanagementplatform.util.TokenUtil;
import com.zhang.datamanagementplatform.util.redis.RedisKeyManagerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author ztc

 * @Date Created in 16:05 2018/11/12
 * @Description:
 */
@Service
public class PermissionServiceImp implements PermissionService {

    /**
     * 系统所有权限缓存时间一天
     */
    private static final long SYSTEM_PERMISSION_CACHE_TIME = 24 * 3600;

    private static final Logger logger = LoggerFactory.getLogger(PermissionServiceImp.class);

    @Autowired
    private ResultUtil resultUtil;
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ResultVO getResourceList() {
        return resultUtil.success(ResourceConstants.RESOURCES);
    }

    @Override
    public ResultVO getActionList() {
        return resultUtil.success(PermissionActionConstant.ACTIONS);
    }

    @Override
    public ResultVO getRoleAndPermissionList() {
        try{
            List<RolePermissionVO> rolePermissionVOS = permissionDao.selectRolePermission();
            return resultUtil.success(rolePermissionVOS);
        }catch (Exception e){
            e.printStackTrace();
            return resultUtil.error(ResultEnum.UNKNOWN_ERROR.getStatus(),e.getMessage());
        }
    }

    @Override
    public ResultVO getUserAndRoleList() {
        try{
            List<UserRoleVO> userRoleVOS = permissionDao.selectUserRole();
            return resultUtil.success(userRoleVOS);
        }catch (Exception e){
            e.printStackTrace();
            return resultUtil.error(ResultEnum.UNKNOWN_ERROR.getStatus(),e.getMessage());
        }
    }

    @Override
    public ResultVO delUserAndRole(String uid,String token) {
        String tempUserId = new TokenUtil().getUserKeyByToken(token);
        try{
            Role role = permissionDao.selectRoleByUserRoleId(uid);
            /**
             * 无法解除自己的关系
             */
            if (uid.equals(tempUserId)){
                return resultUtil.error(ResultEnum.CANNOT_DEL_SELF);
            }
            UserPO actionUser = userDao.selectUserById(tempUserId);
            if ("sAdmin".equals(role.getRoleName())){
                /**
                 * 如果被删除的用户是sAdmin
                 * 操作者的角色也是sAdmin才可删除，否则认为权限不足
                 */
                if ( "sAdmin".equals(actionUser.getRole())){
                    if(permissionDao.delUserAndRole(uid)){
                        return resultUtil.success();
                    }
                }else{
                    return resultUtil.error(ResultEnum.ACTION_UNAUTHORIZED);
                }
            }else{
                /**
                 * 被删除的用户不是sAdmin
                 */
                if(permissionDao.delUserAndRole(uid)){
                    return resultUtil.success();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return resultUtil.error(ResultEnum.UNKNOWN_ERROR.getStatus(),e.getMessage());
        }
        return resultUtil.error(ResultEnum.UNKNOWN_ERROR);
    }

    @Override
    public ResultVO getAllPermission() {
        try{
            List<Permission> redisList = (List<Permission>) ObjectTransformUtil.jsonToList(String.valueOf(redisUtil.get(RedisKeyManagerUtil.getSystemPermissionKey())));
            if (redisList == null || redisList.size() == 0){
                ArrayList<Permission> permissions = permissionDao.selectPermissions();
                if (permissions != null){
                    logger.info("从数据库中读取系统所有权限。");
                    redisUtil.set(RedisKeyManagerUtil.getSystemPermissionKey(), ObjectTransformUtil.listToJson(permissions),SYSTEM_PERMISSION_CACHE_TIME);
                    logger.info("系统所有权限加入缓存成功。");
                    return resultUtil.success(permissions);
                }
            }else{
                logger.info("从缓存读取系统所有权限。");
                return resultUtil.success(redisList);
            }
        }catch (Exception e){
            e.printStackTrace();
            return resultUtil.error(ResultEnum.UNKNOWN_ERROR.getStatus(),e.getMessage());
        }
        return resultUtil.error(ResultEnum.UNKNOWN_ERROR);
    }

    @Transactional
    @Override
    public ResultVO addRole(String roleName, ArrayList<String> permissions) {
        if("sAdmin".equals(roleName)){
            return resultUtil.error(ResultEnum.RESOURCE_EXIST.getStatus(),"角色名不能为sAdmin");
        }
        ArrayList<Role> roles = permissionDao.selectAllRole();
        for (Role tempRole : roles){
            if (tempRole.getRoleName().equals(roleName)){
                return resultUtil.error(ResultEnum.RESOURCE_EXIST.getStatus(),"该角色名已存在");
            }
        }
        Role role = new Role(UUID.randomUUID().toString().replace("-",""),roleName);
        ArrayList<RolePermissionPO> rolePermissions = new ArrayList<>();
        permissionDao.insertRole(role);
        for (int i = 0;i<permissions.size();i++){
            RolePermissionPO tempRolePermission = new RolePermissionPO(
                    UUID.randomUUID().toString().replace("-",""),
                    role.getId(),
                    permissions.get(i)
            );
            rolePermissions.add(tempRolePermission);
        }
        permissionDao.insertRolePermission(rolePermissions);
        return resultUtil.success();
    }

    @Override
    public ResultVO getAllRole() {
        try{
            ArrayList<Role> roles = permissionDao.selectAllRole();
            return resultUtil.success(roles);
        }catch (Exception e){
            e.printStackTrace();
            return resultUtil.error(ResultEnum.UNKNOWN_ERROR.getStatus(),e.getMessage());
        }
    }

    @Transactional
    @Override
    public ResultVO addUserRole(String role, ArrayList<String> userIds,String actionUserId) {
        boolean flag = false;
        /**
         * 遍历检查这些用户是否已有角色
         */
        for (int i = 0;i < userIds.size(); i ++){
            UserRolePO userRolePO = permissionDao.selectUserRoleByUserId(userIds.get(i));
            if (userRolePO != null){
                flag = true;
                permissionDao.updateUserRole(role,userIds.get(i));
            }
        }
        if (flag){
            return resultUtil.error(ResultEnum.RESOURCE_EXIST.getStatus(),"操作用户中包含已有角色的用户，无法操作");
        }else{
            /**
             * 查询将要分配的角色
             */
            Role userRole = permissionDao.selectRoleByRoleId(role);
            /**
             * 如果将要分配的角色是sAdmin，则判断操作者是否是sAdmin，是则允许，否则拦截
             */
            if ("sAdmin".equals(userRole.getRoleName())){
                UserPO actionUser = userDao.selectUserById(actionUserId);
                /**
                 * 操作者也是sAdmin，放行
                 */
                if ("sAdmin".equals(actionUser.getRole())){
                    for (int i = 0;i < userIds.size(); i ++){
                        permissionDao.insertUserRole(UUID.randomUUID().toString().replace("-",""),role,userIds.get(i));
                    }
                }else{
                    return resultUtil.error(ResultEnum.ACTION_UNAUTHORIZED);
                }
            }else{
                for (int i = 0;i < userIds.size(); i ++){
                    permissionDao.insertUserRole(UUID.randomUUID().toString().replace("-",""),role,userIds.get(i));
                }
            }
        }
        return resultUtil.success();
    }

    @Override
    public ResultVO updateUserRole(String roleId, String userId,String actionUserId) {
        UserPO user = userDao.selectUserById(userId);
        /**
         * 如果被操作者是sAdmin
         */
        if ("sAdmin".equals(user.getRole())){
            UserPO actionUser = userDao.selectUserById(actionUserId);
            /**
             * 操作者也是sAdmin则允许
             */
            if ("sAdmin".equals(actionUser.getRole())){
                permissionDao.updateUserRole(roleId,userId);
                return resultUtil.success();
            }else{
                /**
                 * 操作者不是sAdmin没有权限操作sAdmin用户
                 */
                return resultUtil.error(ResultEnum.ACTION_UNAUTHORIZED);
            }
        }else{  // 被操作者不是sAdmin则允许操作
            permissionDao.updateUserRole(roleId,userId);
            return resultUtil.success();
        }
    }
}
