/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package java.nio.file;

/**
 * An object that configures how to copy or move a file.
 *
 * <p> Objects of this type may be used with the {@link
 * Files#copy(Path,Path,CopyOption[]) Files.copy(Path,Path,CopyOption...)},
 * {@link Files#copy(java.io.InputStream,Path,CopyOption[])
 * Files.copy(InputStream,Path,CopyOption...)} and {@link Files#move
 * Files.move(Path,Path,CopyOption...)} methods to configure how a file is
 * copied or moved.
 *
 * <p> The {@link StandardCopyOption} enumeration type defines the
 * <i>standard</i> options.
 *
 * <p>
 *  配置如何复制或移动文件的对象。
 * 
 *  <p>此类型的对象可以与{@link Files#copy(Path,Path,CopyOption [])Files.copy(Path,Path,CopyOption ...)},{@link Files#copy .io.InputStream,Path,CopyOption [])Files.copy(InputStream,Path,CopyOption ...)}
 * 和{@link Files#move Files.move(Path,Path,CopyOption ...)}方法文件被复制或移动。
 * 
 * @since 1.7
 */

public interface CopyOption {
}
