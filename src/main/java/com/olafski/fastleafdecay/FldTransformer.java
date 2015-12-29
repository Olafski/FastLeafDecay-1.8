package com.olafski.fastleafdecay;

import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Level;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;

/**
 * Created by Olaf on 29-12-2015.
 */
public class FldTransformer implements IClassTransformer {
    Logger coreLogger = LogManager.getLogger("FastLeafDecay");

    @Override
    public byte[] transform(String obfName, String transformedName, byte[] basicClass) {
        if (transformedName.equals("net.minecraft.block.BlockLeavesBase"))
        {
            return patchLeafClass(basicClass);
        }

        return basicClass;
    }

    private byte[] patchLeafClass(byte[] basicClass)
    {
        coreLogger.log(Level.INFO, "Patching leaves.");

        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(basicClass);
        classReader.accept(classNode, 0);
        coreLogger.log(Level.INFO, "Found Leave Class: " + classNode.name);

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        classNode.accept(writer);

        String methodName = FldLoadingPlugin.IN_MCP ? "onNeighborBlockChange" : "func_176204_a";

        String worldClass = "net/minecraft/world/World";
        String blockClass = "net/minecraft/block/Block";
        String blockPosClass = "net/minecraft/util/BlockPos";
        String blockStateInterface = "net/minecraft/block/state/IBlockState";

        String methodSignature = "(L" + worldClass + ";L" + blockPosClass + ";L" + blockStateInterface +  ";L" + blockClass + ";)V";
        String fldMethodSignature = "(L" + worldClass + ";L" + blockPosClass + ";L" + blockClass + ";)V";

        MethodVisitor mv = writer.visitMethod(Opcodes.ACC_PUBLIC, methodName, methodSignature, null, null);

        mv.visitCode();

        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(81, l0);
        mv.visitVarInsn(Opcodes.ALOAD, 1);
        mv.visitVarInsn(Opcodes.ALOAD, 2);
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/olafski/fastleafdecay/FldHandler", "handleLeafDecay", fldMethodSignature);
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitLineNumber(82, l1);
        mv.visitInsn(Opcodes.RETURN);
        Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitLocalVariable("this", "Lcom/olafski/fastleafdecay/FldTransformer;", null, l0, l2, 0);
        mv.visitLocalVariable("world", "L" + worldClass + ";", null, l0, l2, 1);
        mv.visitLocalVariable("pos", "L" + blockPosClass + ";", null, l0, l2, 1);
        mv.visitLocalVariable("block", "L" + blockClass + ";", null, l0, l2, 5);
        mv.visitMaxs(5, 6);
        mv.visitEnd();

        return writer.toByteArray();
    }
}
