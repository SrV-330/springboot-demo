package com.springboot.demo.process;

import com.google.auto.service.AutoService;
import com.springboot.demo.annotation.TestAnno;
import com.sun.source.tree.Tree;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Names;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.swing.*;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("com.springboot.demo.annotation.TestAnno")
@AutoService(Processor.class)
public class TestProcess extends AbstractProcessor {

    private Messager messager;
    private JavacTrees javacTrees;
    private TreeMaker treeMaker;
    private Names names;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
        this.javacTrees = JavacTrees.instance(processingEnv);
        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        treeMaker = TreeMaker.instance(context);
        names = Names.instance(context);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> eleAnnoSet = roundEnv.getElementsAnnotatedWith(TestAnno.class);
        eleAnnoSet.forEach(ele -> {
            JCTree jcTree = javacTrees.getTree(ele);
            jcTree.accept(new TreeTranslator() {
                @Override
                public void visitMethodDef(JCTree.JCMethodDecl jcMethodDecl) {

                    messager.printMessage(Diagnostic.Kind.NOTE, "call method: " +
                            jcMethodDecl.getName());
                    List<JCTree.JCVariableDecl> params = jcMethodDecl.params;
                    if (params == null || params.isEmpty()) {
                        super.visitMethodDef(jcMethodDecl);
                        return;
                    }

                    StringBuilder paraSb = new StringBuilder(16);

                    for (JCTree.JCVariableDecl param : params) {
                        if (!Tree.Kind.VARIABLE.equals(param.getKind()))
                            continue;

                        paraSb.append(param.getType() + ": " + param.getName() + "\n");
                    }

                    messager.printMessage(Diagnostic.Kind.NOTE, "paras: [ " +
                            paraSb.toString() + " ]");


                    super.visitMethodDef(jcMethodDecl);
                }
            });
        });

        return true;
    }
}
