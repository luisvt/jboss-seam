package org.jboss.seam.security.permission;

import static org.jboss.seam.ScopeType.APPLICATION;
import static org.jboss.seam.annotations.Install.BUILT_IN;

import java.io.Serializable;
import java.util.List;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.log.LogProvider;
import org.jboss.seam.log.Logging;
import org.jboss.seam.security.Identity;

/**
 * Permission management component, used to grant or revoke permissions on specific objects or of
 * specific permission types to particular users or roles.
 * 
 * @author Shane Bryzak
 */
@Scope(APPLICATION)
@Name("org.jboss.seam.security.permissionManager")
@Install(precedence = BUILT_IN)
public class PermissionManager implements Serializable
{
   public static final String PERMISSION_STORE_COMPONENT_NAME = "permissionStore";
   
   public static final String PERMISSION_PERMISSION_NAME = "seam.permission";
   
   public static final String PERMISSION_READ = "read";
   public static final String PERMISSION_GRANT = "grant";
   public static final String PERMISSION_REVOKE = "revoke";   
   
   private static final LogProvider log = Logging.getLogProvider(PermissionManager.class);
   
   private PermissionStore permissionStore;
   
   @Create
   public void create()
   {
      if (permissionStore == null)
      {
         permissionStore = (PermissionStore) Component.getInstance(PERMISSION_STORE_COMPONENT_NAME, true);
      }         
      
      if (permissionStore == null)
      {
         log.warn("no permission store available - please install a PermissionStore with the name '" +
               PERMISSION_STORE_COMPONENT_NAME + "' if permission management is required.");
      }
   } 
   
   public static PermissionManager instance()
   {
      if ( !Contexts.isApplicationContextActive() )
      {
         throw new IllegalStateException("No active application context");
      }

      PermissionManager instance = (PermissionManager) Component.getInstance(
            PermissionManager.class, ScopeType.APPLICATION);

      if (instance == null)
      {
         throw new IllegalStateException("No PermissionManager could be created");
      }

      return instance;
   }
   
   public PermissionStore getPermissionStore()
   {
      return permissionStore;
   }
   
   public void setPermissionStore(PermissionStore permissionStore)
   {
      this.permissionStore = permissionStore;
   }
   
   public List<Permission> listPermissions(String target, String action)
   {
      Identity.instance().checkPermission(PERMISSION_PERMISSION_NAME, PERMISSION_READ);
      return permissionStore.listPermissions(target, action);
   }
   
   public List<Permission> listPermissions(Object target)
   {
      Identity.instance().checkPermission(PERMISSION_PERMISSION_NAME, PERMISSION_READ);
      return permissionStore.listPermissions(target);
   }
   
   public boolean grantPermission(Permission permission)
   {
      Identity.instance().checkPermission(permission, PERMISSION_GRANT);
      return permissionStore.grantPermission(permission);
   }
   
   public boolean revokePermission(Permission permission)
   {
      Identity.instance().checkPermission(permission, PERMISSION_REVOKE);
      return permissionStore.revokePermission(permission);
   }
}
