//
//  TranslateTextField.swift
//  iosApp
//
//  Created by Anjali Prajapati on 09/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared
import UniformTypeIdentifiers

struct TranslateTextField: View {
    
    @Binding var fromText:String
    
    let toText:String?
    let isTranslating:Bool
    let fromLanguage:UiLanguage
    let toLanguage:UiLanguage
    let onTranslateEvent:(TranslateEvent) -> Void
    

    
    var body: some View {
        if(toText == nil || isTranslating){
            IdleTextField(
                fromText: $fromText,
                isTranslating: isTranslating,
                onTranslateEvent: onTranslateEvent
            )
            .gradientSurface()
            .cornerRadius(15)
            .animation(.easeInOut, value: isTranslating)
            .shadow(radius: 4)
        }else{
            TranslatedTextField(
                fromText: fromText,
                toText: toText ?? "Helo World!",
                fromLangauge:fromLanguage,
                toLangauge: toLanguage,
                onTranslateEvent:onTranslateEvent
            )
            .padding()
            .gradientSurface()
            .cornerRadius(15)
            .animation(.easeInOut, value: isTranslating)
            .shadow(radius: 4)
            .onTapGesture {
                onTranslateEvent(TranslateEvent.EditTranslation())
            }
        }
    }
}

#Preview {
    TranslateTextField(
        fromText: Binding(get: {"test"}, set: { value in }),
        toText: "Translated Text",
        isTranslating:false,
        fromLanguage: UiLanguage(language: .english, imageName: "english"),
        toLanguage: UiLanguage(language: .hindi, imageName: "hindi")
    ) { event in
            
    }
}


private extension TranslateTextField {
    
    struct IdleTextField : View {
        
        @Binding var fromText:String
        let isTranslating:Bool
        let onTranslateEvent:(TranslateEvent) -> Void
        
        var body: some View {
            
            TextEditor(text:$fromText)
                .frame(maxWidth: .infinity,minHeight: 200,alignment: .topLeading)
                .padding()
                .foregroundStyle(Color.onSurface)
                .overlay (alignment: .bottomTrailing){
                    ProgressButton(
                        text: "Translate",
                        isLoading:isTranslating,
                        onClick: {
                            onTranslateEvent(TranslateEvent.Translate())
                        }
                    )
                    .padding(.trailing)
                    .padding(.bottom)
                }
                .onAppear{
                    UITextView.appearance().backgroundColor = .clear
                }
            
        }
    }
        
        
    struct TranslatedTextField  : View {
        
        let fromText:String
        let toText:String
        let fromLangauge:UiLanguage
        let toLangauge:UiLanguage
        let onTranslateEvent:(TranslateEvent) -> Void
        
        private let tts : TextToSpeech = TextToSpeech()
        
        var body: some View {
            VStack(alignment: .leading){
                LanguageDisplay(language: fromLangauge)
                Text(fromText)
                    .foregroundStyle(Color.onSurface)
                HStack{
                    Spacer()
                    Button(action: {
                        UIPasteboard.general.setValue(fromText, forPasteboardType: UTType.plainText.identifier)
                    }){
                       
                        Image(uiImage: UIImage(named: "copy")!)
                            .renderingMode(.template)
                            .foregroundStyle(Color.lightBlue)
                    }
                    
                    Button(action: {
                        onTranslateEvent(TranslateEvent.CloseTranslation())
                    }){
                       
                        Image(systemName: "xmark")
                            .foregroundStyle(Color.lightBlue)
                    }
                }
                Divider()
                    .padding()
                LanguageDisplay(language: toLangauge)
                    .padding(.bottom)
                Text(toText)
                    .foregroundStyle(Color.onSurface)
                HStack{
                    Spacer()
                    Button(action: {
                        UIPasteboard.general.setValue(toText, forPasteboardType: UTType.plainText.identifier)
                    }){
                       
                        Image(uiImage: UIImage(named: "copy")!)
                            .renderingMode(.template)
                            .foregroundStyle(Color.lightBlue)
                    }
                    
                    Button(action: {
                        tts.speak(
                            text: toText,
                            language: toLangauge.language.langCode
                        )
                    }){
                       
                        Image(systemName: "speaker.wave.2")
                            .foregroundStyle(Color.lightBlue)
                    }
                }
                
            }
        }
        
    }
    
}


