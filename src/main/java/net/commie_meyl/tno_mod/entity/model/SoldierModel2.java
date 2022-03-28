//package net.commie_meyl.tno_mod.entity.model;
//
//import com.mojang.blaze3d.matrix.MatrixStack;
//import com.mojang.blaze3d.vertex.IVertexBuilder;
//import net.minecraft.client.renderer.entity.model.EntityModel;
//import net.minecraft.client.renderer.model.ModelRenderer;
//import net.minecraft.util.math.MathHelper;
//import net.commie_meyl.tno_mod.entity.custom.SoldierEntity;
//
//public class SoldierModel2<T extends SoldierEntity> extends EntityModel<T> {
//	private final ModelRenderer Head;
//	private final ModelRenderer Body;
//	private final ModelRenderer RightArm;
//	private final ModelRenderer LeftArm;
//	private final ModelRenderer RightLeg;
//	private final ModelRenderer LeftLeg;
//
//	public SoldierModel2() {
//		texWidth = 64;
//		texHeight = 64;
//
//		Head = new ModelRenderer(this);
//		Head.setPos(0.0F, 0.0F, 0.0F);
//		setRotationAngle(Head, -0.1047F, 0.0873F, 0.0F);
//		Head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
//		Head.texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.5F, false);
//
//		Body = new ModelRenderer(this);
//		Body.setPos(0.0F, 0.0F, 0.0F);
//		Body.texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
//		Body.texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.25F, false);
//
//		RightArm = new ModelRenderer(this);
//		RightArm.setPos(-5.0F, 2.0F, 0.0F);
//		setRotationAngle(RightArm, -0.1745F, 0.0F, 0.0F);
//		RightArm.texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
//		RightArm.texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);
//
//		LeftArm = new ModelRenderer(this);
//		LeftArm.setPos(5.0F, 2.0F, 0.0F);
//		setRotationAngle(LeftArm, 0.2094F, 0.0F, 0.0F);
//		LeftArm.texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
//		LeftArm.texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);
//
//		RightLeg = new ModelRenderer(this);
//		RightLeg.setPos(-1.9F, 12.0F, 0.0F);
//		setRotationAngle(RightLeg, 0.192F, 0.0F, 0.0349F);
//		RightLeg.texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
//		RightLeg.texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);
//
//		LeftLeg = new ModelRenderer(this);
//		LeftLeg.setPos(1.9F, 12.0F, 0.0F);
//		setRotationAngle(LeftLeg, -0.1745F, 0.0F, -0.0349F);
//		LeftLeg.texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
//		LeftLeg.texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);
//	}
//
//	@Override
//	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount,
//								  float ageInTicks, float netHeadYaw, float headPitch) {
//		this.Head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
//		this.Head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
//		this.RightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
//		this.LeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
//	}
//
//
////	@Override
////	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
////		Head.render(matrixStack, buffer, packedLight, packedOverlay);
////		Body.render(matrixStack, buffer, packedLight, packedOverlay);
////		RightArm.render(matrixStack, buffer, packedLight, packedOverlay);
////		LeftArm.render(matrixStack, buffer, packedLight, packedOverlay);
////		RightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
////		LeftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
////	}
//
//	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
//		modelRenderer.xRot = x;
//		modelRenderer.yRot = y;
//		modelRenderer.zRot = z;
//	}
//
//	@Override
//	public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int i, int i1, float v, float v1, float v2, float v3) {
//
//	}
//}