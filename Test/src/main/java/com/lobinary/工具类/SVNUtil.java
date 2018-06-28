package com.lobinary.工具类;

import java.io.File;

import org.apache.log4j.Logger;
import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNStatus;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

/**
 * SVNKit Utility
 *
 */
public class SVNUtil {
    
    private static Logger logger = Logger.getLogger(SVNUtil.class);
    
    
    public static void main(String[] args) throws SVNException {
//        getChildren();
        getChildrenTrunk();
    }


    private static void getChildren() throws SVNException {
        String svnRoot = "http://172.17.103.2/YeePay/dev/app";
        File localFolder = new File("H:/SVN/YeePay/dev/app");
        String username = "bin.lv";
        String password = "1234qwer";
        SVNClientManager clientManager = authSvn(svnRoot, username, password);
        long checkout = checkout(clientManager, SVNURL.parseURIEncoded(svnRoot), SVNRevision.HEAD, localFolder, SVNDepth.IMMEDIATES);
//            long checkout = update(clientManager, localPath, SVNRevision.HEAD, SVNDepth.IMMEDIATES);
        System.out.println(checkout);
        System.out.println("-------------------------------------------------------------");
    }
    private static void getChildrenTrunk() throws SVNException {
        String svnRoot = "http://172.17.103.2/YeePay/dev/app";
        File localFolder = new File("H:/SVN/YeePay/dev/app");
        String username = "bin.lv";
        String password = "1234qwer";
        SVNClientManager clientManager = authSvn(svnRoot, username, password);
        File[] ps = localFolder.listFiles();
        for(File f : ps){
            String svnUrl = svnRoot+"/"+f.getName()+"/trunk";
            File localPath = new File(f.getAbsolutePath()+"\\trunk");
//            String svnUrl = svnRoot+"/"+f.getName();
//            File localPath = new File(f.getAbsolutePath());
            if(f.getName().contains("svn")){
                continue;
            }
//            String svnUrl = svnRoot+"/"+f.getName();
//            File localPath = new File(f.getAbsolutePath());
            System.out.println("svn路径："+svnUrl);
            System.out.println("本地路径："+localPath);
            long checkout = checkout(clientManager, SVNURL.parseURIEncoded(svnUrl), SVNRevision.HEAD, localPath, SVNDepth.INFINITY);
//            long checkout = update(clientManager, localPath, SVNRevision.HEAD, SVNDepth.IMMEDIATES);
            System.out.println(checkout);
            System.out.println("-------------------------------------------------------------");
        }
//        long update = update(clientManager, localFolder,  SVNRevision.HEAD, SVNDepth.INFINITY);
//        System.out.println(update);
    }
    
    
    /**
     * 通过不同的协议初始化版本库
     */
    public static void setupLibrary() {
        DAVRepositoryFactory.setup();
        SVNRepositoryFactoryImpl.setup();
        FSRepositoryFactory.setup();
    }

    /**
     * 验证登录svn
     */
    public static SVNClientManager authSvn(String svnRoot, String username,
            String password) {
        // 初始化版本库
        setupLibrary();

        // 创建库连接
        SVNRepository repository = null;
        try {
            repository = SVNRepositoryFactory.create(SVNURL
                    .parseURIEncoded(svnRoot));
        } catch (SVNException e) {
            logger.error(e.getErrorMessage(), e);
            return null;
        }

        // 身份验证
        ISVNAuthenticationManager authManager = SVNWCUtil

        .createDefaultAuthenticationManager(username, password);

        // 创建身份验证管理器
        repository.setAuthenticationManager(authManager);

        DefaultSVNOptions options = SVNWCUtil.createDefaultOptions(true);
        SVNClientManager clientManager = SVNClientManager.newInstance(options,
                authManager);
        return clientManager;
    }
    
    /**
     * Make directory in svn repository
     * @param clientManager
     * @param url 
     *          eg: http://svn.ambow.com/wlpt/bsp/trunk 
     * @param commitMessage
     * @return
     * @throws SVNException
     */
    public static SVNCommitInfo makeDirectory(SVNClientManager clientManager,
            SVNURL url, String commitMessage) {
        try {
            return clientManager.getCommitClient().doMkDir(
                    new SVNURL[] { url }, commitMessage);
        } catch (SVNException e) {
            logger.error(e.getErrorMessage(), e);
        }
        return null;
    }
    
    /**
     * Imports an unversioned directory into a repository location denoted by a
     *  destination URL
     * @param clientManager
     * @param localPath
     *          a local unversioned directory or singal file that will be imported into a 
     *          repository;
     * @param dstURL
     *          a repository location where the local unversioned directory/file will be 
     *          imported into
     * @param commitMessage
     * @param isRecursive 递归
     * @return
     */
    public static SVNCommitInfo importDirectory(SVNClientManager clientManager,
            File localPath, SVNURL dstURL, String commitMessage,
            boolean isRecursive) {
        try {
            return clientManager.getCommitClient().doImport(localPath, dstURL,
                    commitMessage, null, true, true,
                    SVNDepth.fromRecurse(isRecursive));
        } catch (SVNException e) {
            logger.error(e.getErrorMessage(), e);
        }
        return null;
    }
    
    /**
     * Puts directories and files under version control
     * @param clientManager
     *          SVNClientManager
     * @param wcPath 
     *          work copy path
     */
    public static void addEntry(SVNClientManager clientManager, File wcPath) {
        try {
            clientManager.getWCClient().doAdd(new File[] { wcPath }, true,
                    false, false, SVNDepth.INFINITY, false, false,
                    true);
        } catch (SVNException e) {
            logger.error(e.getErrorMessage(), e);
        }
    }
       
    /**
     * Collects status information on a single Working Copy item
     * @param clientManager
     * @param wcPath
     *          local item's path
     * @param remote
     *          true to check up the status of the item in the repository, 
     *          that will tell if the local item is out-of-date (like '-u' option in the SVN client's 
     *          'svn status' command), otherwise false
     * @return
     * @throws SVNException
     */
    public static SVNStatus showStatus(SVNClientManager clientManager,
            File wcPath, boolean remote) {
        SVNStatus status = null;
        try {
            status = clientManager.getStatusClient().doStatus(wcPath, remote);
        } catch (SVNException e) {
            logger.error(e.getErrorMessage(), e);
        }
        return status;
    }
    
    /**
     * Commit work copy's change to svn
     * @param clientManager
     * @param wcPath 
     *          working copy paths which changes are to be committed
     * @param keepLocks
     *          whether to unlock or not files in the repository
     * @param commitMessage
     *          commit log message
     * @return
     * @throws SVNException
     */
    public static SVNCommitInfo commit(SVNClientManager clientManager,
            File wcPath, boolean keepLocks, String commitMessage) {
        try {
            return clientManager.getCommitClient().doCommit(
                    new File[] { wcPath }, keepLocks, commitMessage, null,
                    null, false, false, SVNDepth.INFINITY);
        } catch (SVNException e) {
            logger.error(e.getErrorMessage(), e);
        }
        return null;
    }
    
    /**
     * Updates a working copy (brings changes from the repository into the working copy).
     * @param clientManager
     * @param wcPath
     *          working copy path
     * @param updateToRevision
     *          revision to update to
     * @param depth
     *          update的深度：目录、子目录、文件
     * @return
     * @throws SVNException
     */
    public static long update(SVNClientManager clientManager, File wcPath,
            SVNRevision updateToRevision, SVNDepth depth) {
        SVNUpdateClient updateClient = clientManager.getUpdateClient();

        /*
         * sets externals not to be ignored during the update
         */
        updateClient.setIgnoreExternals(false);

        /*
         * returns the number of the revision wcPath was updated to
         */
        try {
            return updateClient.doUpdate(wcPath, updateToRevision,depth, false, false);
        } catch (SVNException e) {
            logger.error(e.getErrorMessage(), e);
        }
        return 0;
    }
    
    /**
     * recursively checks out a working copy from url into wcDir
     * @param clientManager
     * @param url
     *          a repository location from where a Working Copy will be checked out
     * @param revision
     *          the desired revision of the Working Copy to be checked out
     * @param destPath
     *          the local path where the Working Copy will be placed
     * @param depth
     *          checkout的深度，目录、子目录、文件
     * @return
     * @throws SVNException
     */
    public static long checkout(SVNClientManager clientManager, SVNURL url,
            SVNRevision revision, File destPath, SVNDepth depth) {

        SVNUpdateClient updateClient = clientManager.getUpdateClient();
        /*
         * sets externals not to be ignored during the checkout
         */
        updateClient.setIgnoreExternals(false);
        /*
         * returns the number of the revision at which the working copy is
         */
        try {
            return updateClient.doCheckout(url, destPath, revision, revision,depth, false);
        } catch (SVNException e) {
            logger.error(e.getErrorMessage(), e);
        }
        return 0;
    }
    
    /**
     * 确定path是否是一个工作空间
     * @param path
     * @return
     */
    public static boolean isWorkingCopy(File path){
        if(!path.exists()){
            logger.warn("'" + path + "' not exist!");
            return false;
        }
        try {
            if(null == SVNWCUtil.getWorkingCopyRoot(path, false)){
                return false;
            }
        } catch (SVNException e) {
            logger.error(e.getErrorMessage(), e);
        }
        return true;
    }
    
    /**
     * 确定一个URL在SVN上是否存在
     * @param url
     * @return
     */
    public static boolean isURLExist(SVNURL url,String username,String password){
        try {
            SVNRepository svnRepository = SVNRepositoryFactory.create(url);
            ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password);
            svnRepository.setAuthenticationManager(authManager);
            SVNNodeKind nodeKind = svnRepository.checkPath("", -1);
            return nodeKind == SVNNodeKind.NONE ? false : true; 
        } catch (SVNException e) {
            e.printStackTrace();
        }
        return false;
    }

}