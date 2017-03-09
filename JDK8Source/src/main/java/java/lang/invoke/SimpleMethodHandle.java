/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2008, 2012, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.invoke;

import static java.lang.invoke.LambdaForm.BasicType.*;
import static java.lang.invoke.MethodHandleStatics.*;

/**
 * A method handle whose behavior is determined only by its LambdaForm.
 * <p>
 *  行为仅由其LambdaForm确定的方法句柄。
 * 
 * 
 * @author jrose
 */
final class SimpleMethodHandle extends BoundMethodHandle {
    private SimpleMethodHandle(MethodType type, LambdaForm form) {
        super(type, form);
    }

    /*non-public*/ static BoundMethodHandle make(MethodType type, LambdaForm form) {
        return new SimpleMethodHandle(type, form);
    }

    /* <p>
    /*  return new SimpleMethodHandle(type,form); }}
    /* 
    /* 
    /*non-public*/ static final SpeciesData SPECIES_DATA = SpeciesData.EMPTY;

    /* <p>
    /* 
    /*non-public*/ public SpeciesData speciesData() {
            return SPECIES_DATA;
    }

    @Override
    /* <p>
    /*  返回SPECIES_DATA; }}
    /* 
    /*  @覆盖
    /* 
    /* 
    /*non-public*/ BoundMethodHandle copyWith(MethodType mt, LambdaForm lf) {
        return make(mt, lf);
    }

    @Override
    String internalProperties() {
        return "\n& Class="+getClass().getSimpleName();
    }

    @Override
    /* <p>
    /*  return make(mt,lf); }}
    /* 
    /*  @Override String internalProperties(){return"\ n&Class ="+ getClass()。getSimpleName(); }}
    /* 
    /*  @覆盖
    /* 
    /* 
    /*non-public*/ public int fieldCount() {
        return 0;
    }

    @Override
    /* <p>
    /*  return 0; }}
    /* 
    /*  @覆盖
    /* 
    /* 
    /*non-public*/ final BoundMethodHandle copyWithExtendL(MethodType mt, LambdaForm lf, Object narg) {
        return BoundMethodHandle.bindSingle(mt, lf, narg); // Use known fast path.
    }
    @Override
    /* <p>
    /*  return BoundMethodHandle.bindSingle(mt,lf,narg); //使用已知的快速路径。 } @覆盖
    /* 
    /* 
    /*non-public*/ final BoundMethodHandle copyWithExtendI(MethodType mt, LambdaForm lf, int narg) {
        try {
            return (BoundMethodHandle) SPECIES_DATA.extendWith(I_TYPE).constructor().invokeBasic(mt, lf, narg);
        } catch (Throwable ex) {
            throw uncaughtException(ex);
        }
    }
    @Override
    /* <p>
    /*  try {return(BoundMethodHandle)SPECIES_DATA.extendWith(I_TYPE).constructor()。
    /* invokeBasic(mt,lf,narg); } catch(Throwable ex){throw uncaughtException(ex); }} @覆盖。
    /* 
    /* 
    /*non-public*/ final BoundMethodHandle copyWithExtendJ(MethodType mt, LambdaForm lf, long narg) {
        try {
            return (BoundMethodHandle) SPECIES_DATA.extendWith(J_TYPE).constructor().invokeBasic(mt, lf, narg);
        } catch (Throwable ex) {
            throw uncaughtException(ex);
        }
    }
    @Override
    /* <p>
    /*  try {return(BoundMethodHandle)SPECIES_DATA.extendWith(J_TYPE).constructor()。
    /* invokeBasic(mt,lf,narg); } catch(Throwable ex){throw uncaughtException(ex); }} @覆盖。
    /* 
    /* 
    /*non-public*/ final BoundMethodHandle copyWithExtendF(MethodType mt, LambdaForm lf, float narg) {
        try {
            return (BoundMethodHandle) SPECIES_DATA.extendWith(F_TYPE).constructor().invokeBasic(mt, lf, narg);
        } catch (Throwable ex) {
            throw uncaughtException(ex);
        }
    }
    @Override
    /* <p>
    /*  try {return(BoundMethodHandle)SPECIES_DATA.extendWith(F_TYPE).constructor()。
    /* invokeBasic(mt,lf,narg); } catch(Throwable ex){throw uncaughtException(ex); }} @覆盖。
    /* 
    /*non-public*/ final BoundMethodHandle copyWithExtendD(MethodType mt, LambdaForm lf, double narg) {
        try {
            return (BoundMethodHandle) SPECIES_DATA.extendWith(D_TYPE).constructor().invokeBasic(mt, lf, narg);
        } catch (Throwable ex) {
            throw uncaughtException(ex);
        }
    }
}
