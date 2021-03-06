/*******************************************************************************
 * Copyright (c) 2006-2014
 * Software Technology Group, Dresden University of Technology
 * DevBoost GmbH, Berlin, Amtsgericht Charlottenburg, HRB 140026
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Software Technology Group - TU Dresden, Germany;
 *   DevBoost GmbH - Berlin, Germany
 *      - initial API and implementation
 ******************************************************************************/
package org.emftext.sdk.codegen.resource.ui.generators.ui;

import static de.devboost.codecomposers.java.ClassNameConstants.LINKED_HASH_MAP;
import static de.devboost.codecomposers.java.ClassNameConstants.MAP;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.FIELD;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.FILE_LOCATOR;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.ILLEGAL_ACCESS_EXCEPTION;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.ILLEGAL_ARGUMENT_EXCEPTION;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.I_PATH;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.MALFORMED_URL_EXCEPTION;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.NO_SUCH_FIELD_EXCEPTION;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.PATH;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.SECURITY_EXCEPTION;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.URL;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.IMAGE;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.IMAGE_DESCRIPTOR;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.I_SHARED_IMAGES;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.PLATFORM_UI;

import org.emftext.sdk.codegen.parameters.ArtifactParameter;
import org.emftext.sdk.codegen.resource.GenerationContext;
import org.emftext.sdk.codegen.resource.ui.generators.UIJavaBaseGenerator;

import de.devboost.codecomposers.java.JavaComposite;

public class ImageProviderGenerator extends UIJavaBaseGenerator<ArtifactParameter<GenerationContext>> {

	public void generateJavaContents(JavaComposite sc) {
		sc.add("package " + getResourcePackageName() + ";");sc.addLineBreak();sc.addImportsPlaceholder();
		sc.addLineBreak();
		sc.addJavadoc(
			"A provider class for all images that are required by the generated UI plug-in. " +
			"The default implementation load images from the bundle and caches them to make sure " +
			"each image is loaded at most once."
		);
		sc.add("public class " + getResourceClassName() + " {");
		sc.addLineBreak();
		addFields(sc);
		addMethods(sc);
		sc.add("}");
	}

	private void addMethods(JavaComposite sc) {
		addGetImageMethod(sc);
		addGetImageDescriptorMethod(sc);
	}

	private void addGetImageDescriptorMethod(JavaComposite sc) {
		sc.addJavadoc("Returns the image for the given key. " +
			"Possible keys are:",
			"<ul>",
			"<li>platform:/plugin/your.plugin/icons/yourIcon.png</li>",
			"<li>bundleentry://557.fwk3560063/icons/yourIcon.png</li>",
			"</ul>"
		);
		sc.add("public " + IMAGE_DESCRIPTOR(sc) + " getImageDescriptor(String key) {");
		sc.add(I_PATH(sc) + " path = new " + PATH(sc) + "(key);");
		sc.add(uiPluginActivatorClassName + " plugin = " + uiPluginActivatorClassName + ".getDefault();");
		sc.add("if (plugin == null) {");
		sc.add("return null;");
		sc.add("}");
		sc.addLineBreak();
		sc.add(IMAGE_DESCRIPTOR(sc) + " descriptor = " + IMAGE_DESCRIPTOR(sc) + ".createFromURL(" + FILE_LOCATOR(sc) + ".find(plugin.getBundle(), path, null));");
		sc.add("if (" + IMAGE_DESCRIPTOR(sc) + ".getMissingImageDescriptor().equals(descriptor) || descriptor == null) {");
		sc.addComment("try loading image from any bundle");
		sc.add("try {");
		sc.add(URL(sc) + " pluginUrl = new " + URL(sc) + "(key);");
		sc.add("descriptor = " + IMAGE_DESCRIPTOR(sc) + ".createFromURL(pluginUrl);");
		sc.add("if (" + IMAGE_DESCRIPTOR(sc) + ".getMissingImageDescriptor().equals(descriptor) || descriptor == null) {");
		sc.add("return null;");
		sc.add("}");
		sc.add("} catch (" + MALFORMED_URL_EXCEPTION(sc) + " mue) {");
		sc.add(uiPluginActivatorClassName + ".logError(\"IconProvider can't load image (URL is malformed).\", mue);");
		sc.add("}");
		sc.add("}");
		sc.add("return descriptor;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addFields(JavaComposite sc) {
		sc.add("public final static " + getResourceClassName() + " INSTANCE = new " + getResourceClassName() + "();");
		sc.addLineBreak();
		sc.add("private " + MAP(sc) + "<String, " + IMAGE(sc) + "> imageCache = new " + LINKED_HASH_MAP(sc) + "<String, " + IMAGE(sc) + ">();");
		sc.addLineBreak();
	}

	private void addGetImageMethod(JavaComposite sc) {
		sc.addJavadoc(
			"Returns the image associated with the given key. " +
			"The key can be either a path to an image file in the resource bundle or " +
			"a shared image from " + I_SHARED_IMAGES(null) + "."
		);
		sc.add("public " + IMAGE(sc) + " getImage(String key) {");
		sc.add("if (key == null) {");
		sc.add("return null;");
		sc.add("}");
		sc.add(IMAGE(sc) + " image = null;");
		sc.addComment("try shared images");
		sc.add("try {");
		sc.add(FIELD(sc) + " declaredField = " + I_SHARED_IMAGES(sc) + ".class.getDeclaredField(key);");
		sc.add("Object valueObject = declaredField.get(null);");
		sc.add("if (valueObject instanceof String) {");
		sc.add("String value = (String) valueObject;");
		sc.add("image = " + PLATFORM_UI(sc) + ".getWorkbench().getSharedImages().getImage(value);");
		sc.add("}");
		sc.add("} catch (" + SECURITY_EXCEPTION(sc) + " e) {");
		sc.add("} catch (" + NO_SUCH_FIELD_EXCEPTION(sc) + " e) {");
		sc.add("} catch (" + ILLEGAL_ARGUMENT_EXCEPTION(sc) + " e) {");
		sc.add("} catch (" + ILLEGAL_ACCESS_EXCEPTION(sc) + " e) {");
		sc.add("}");
		sc.add("if (image != null) {");
		sc.add("return image;");
		sc.add("}");
		sc.addLineBreak();
		sc.addComment("try cache");
		sc.add("if (imageCache.containsKey(key)) {");
		sc.add("return imageCache.get(key);");
		sc.add("}");
		sc.addLineBreak();
		sc.addComment("try loading image from UI bundle");
		sc.add(IMAGE_DESCRIPTOR(sc) + " descriptor = getImageDescriptor(key);");
		sc.add("if (descriptor == null) {");
		sc.add("return null;");
		sc.add("}");
		sc.add("image = descriptor.createImage();");
		sc.add("if (image == null) {");
		sc.add("return null;");
		sc.add("}");
		sc.add("imageCache.put(key, image);");
		sc.add("return image;");
		sc.add("}");
		sc.addLineBreak();
	}
}
