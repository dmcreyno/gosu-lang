/*
 * This file is a shadowed version of the older javadoc codebase on which gosudoc is based; borrowed from jdk 9.
 */

package gw.gosudoc.com.sun.tools.javadoc.main;

import com.sun.tools.javac.code.Attribute;
import com.sun.tools.javac.code.Attribute.TypeCompound;
import com.sun.tools.javac.code.Kinds.KindSelector;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Symbol.ClassSymbol;
import com.sun.tools.javac.code.Symbol.MethodSymbol;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.code.Type.TypeVar;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;
import gw.gosudoc.com.sun.javadoc.TypeVariable;

/**
 * Implementation of <code>TypeVariable</code>, which
 * represents a type variable.
 *
 *  <p><b>This is NOT part of any supported API.
 *  If you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 *
 * @author Scott Seligman
 * @since 1.5
 */
@Deprecated
public class TypeVariableImpl extends gw.gosudoc.com.sun.tools.javadoc.main.AbstractTypeImpl implements gw.gosudoc.com.sun.javadoc.TypeVariable
{

    TypeVariableImpl( gw.gosudoc.com.sun.tools.javadoc.main.DocEnv env, TypeVar type) {
        super(env, type);
    }

    /**
     * Return the bounds of this type variable.
     */
    public gw.gosudoc.com.sun.javadoc.Type[] bounds() {
        return TypeMaker.getTypes(env, getBounds((TypeVar)type, env));
    }

    /**
     * Return the class, interface, method, or constructor within
     * which this type variable is declared.
     */
    public gw.gosudoc.com.sun.javadoc.ProgramElementDoc owner() {
        Symbol osym = type.tsym.owner;
        if (osym.kind.matches(KindSelector.TYP)) {
            return env.getClassDoc((ClassSymbol)osym);
        }
        Names names = osym.name.table.names;
        if (osym.name == names.init) {
            return env.getConstructorDoc((MethodSymbol)osym);
        } else {
            return env.getMethodDoc((MethodSymbol)osym);
        }
    }

    /**
     * Return the ClassDoc of the erasure of this type variable.
     */
    @Override
    public gw.gosudoc.com.sun.javadoc.ClassDoc asClassDoc() {
        return env.getClassDoc((ClassSymbol)env.types.erasure(type).tsym);
    }

    @Override
    public TypeVariable asTypeVariable() {
        return this;
    }

    @Override
    public String toString() {
        return typeVarToString(env, (TypeVar)type, true);
    }


    /**
     * Return the string form of a type variable along with any
     * "extends" clause.  Class names are qualified if "full" is true.
     */
    static String typeVarToString( DocEnv env, TypeVar v, boolean full) {
        StringBuilder s = new StringBuilder(v.toString());
        List<Type> bounds = getBounds(v, env);
        if (bounds.nonEmpty()) {
            boolean first = true;
            for (Type b : bounds) {
                s.append(first ? " extends " : " & ");
                s.append( TypeMaker.getTypeString(env, b, full));
                first = false;
            }
        }
        return s.toString();
    }

    /**
     * Get the bounds of a type variable as listed in the "extends" clause.
     */
    private static List<Type> getBounds(TypeVar v, DocEnv env) {
        final Type upperBound = v.getUpperBound();
        Name boundname = upperBound.tsym.getQualifiedName();
        if (boundname == boundname.table.names.java_lang_Object
            && !upperBound.isAnnotated()) {
            return List.nil();
        } else {
            return env.types.getBounds(v);
        }
    }

    /**
     * Get the annotations of this program element.
     * Return an empty array if there are none.
     */
    public gw.gosudoc.com.sun.javadoc.AnnotationDesc[] annotations() {
        if (!type.isAnnotated()) {
            return new gw.gosudoc.com.sun.javadoc.AnnotationDesc[0];
        }
        List<? extends TypeCompound> tas = type.getAnnotationMirrors();
        gw.gosudoc.com.sun.javadoc.AnnotationDesc res[] = new gw.gosudoc.com.sun.javadoc.AnnotationDesc[tas.length()];
        int i = 0;
        for (Attribute.Compound a : tas) {
            res[i++] = new AnnotationDescImpl(env, a);
        }
        return res;
    }
}