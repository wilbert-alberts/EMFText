package org.emftext.sdk.codegen.antlr;

import static org.emftext.sdk.Constants.ANTLR_RUNTIME_DEBUG_PACKAGE;
import static org.emftext.sdk.Constants.ANTLR_RUNTIME_MISC_PACKAGE;
import static org.emftext.sdk.Constants.ANTLR_RUNTIME_PACKAGE;
import static org.emftext.sdk.Constants.ANTLR_RUNTIME_TREE_PACKAGE;

import org.emftext.sdk.IPluginDescriptor;
import org.emftext.sdk.codegen.ArtifactDescriptor;
import org.emftext.sdk.codegen.creators.BuildPropertiesCreator;
import org.emftext.sdk.codegen.creators.DotClasspathCreator;
import org.emftext.sdk.codegen.creators.DotProjectCreator;
import org.emftext.sdk.codegen.creators.ManifestCreator;
import org.emftext.sdk.codegen.parameters.BuildPropertiesParameters;
import org.emftext.sdk.codegen.parameters.ClassPathParameters;
import org.emftext.sdk.codegen.parameters.ManifestParameters;
import org.emftext.sdk.concretesyntax.OptionTypes;

/**
 * This class holds constants for all artifacts that are created for the
 * org.emfext.commons.antlr_version plug-in.
 */
public class ANTLRPluginArtifacts {

	public final static IPluginDescriptor ANTLR_PLUGIN = new IPluginDescriptor() {
		// TODO mseifert: check whether OptionTypes.ANTLR_PLUGIN_ID still works
		public String getName() {
			return "org.emftext.commons.antlr3_2_0";
		}
	};

	public final static ArtifactDescriptor<ANTLRGenerationContext, BuildPropertiesParameters> BUILD_PROPERTIES = new ArtifactDescriptor<ANTLRGenerationContext, BuildPropertiesParameters>(null, BuildPropertiesCreator.FILENAME, "", null, OptionTypes.OVERRIDE_BUILD_PROPERTIES); 
	public final static ArtifactDescriptor<ANTLRGenerationContext, ClassPathParameters> DOT_CLASSPATH = new ArtifactDescriptor<ANTLRGenerationContext, ClassPathParameters>(null, DotClasspathCreator.FILENAME, "", null, OptionTypes.OVERRIDE_DOT_CLASSPATH);
	public final static ArtifactDescriptor<ANTLRGenerationContext, IPluginDescriptor> DOT_PROJECT = new ArtifactDescriptor<ANTLRGenerationContext, IPluginDescriptor>(null, DotProjectCreator.FILENAME, "", null, OptionTypes.OVERRIDE_DOT_PROJECT);
	public final static ArtifactDescriptor<ANTLRGenerationContext, ManifestParameters> MANIFEST = new ArtifactDescriptor<ANTLRGenerationContext, ManifestParameters>(null, ManifestCreator.FILENAME, "", null, OptionTypes.OVERRIDE_MANIFEST);

	public final static ArtifactDescriptor<ANTLRGenerationContext, Object> PACKAGE_ANTLR_RUNTIME = new ArtifactDescriptor<ANTLRGenerationContext, Object>(ANTLR_RUNTIME_PACKAGE, "", "", null, null);
	public final static ArtifactDescriptor<ANTLRGenerationContext, Object> PACKAGE_ANTLR_RUNTIME_DEBUG = new ArtifactDescriptor<ANTLRGenerationContext, Object>(ANTLR_RUNTIME_DEBUG_PACKAGE, "", "", null, null);
	public final static ArtifactDescriptor<ANTLRGenerationContext, Object> PACKAGE_ANTLR_RUNTIME_MISC = new ArtifactDescriptor<ANTLRGenerationContext, Object>(ANTLR_RUNTIME_MISC_PACKAGE, "", "", null, null);
	public final static ArtifactDescriptor<ANTLRGenerationContext, Object> PACKAGE_ANTLR_RUNTIME_TREE = new ArtifactDescriptor<ANTLRGenerationContext, Object>(ANTLR_RUNTIME_TREE_PACKAGE, "", "", null, null); 
}
