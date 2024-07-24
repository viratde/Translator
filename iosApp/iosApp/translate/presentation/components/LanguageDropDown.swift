//
//  LanguageDropDown.swift
//  iosApp
//
//  Created by Anjali Prajapati on 09/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LanguageDropDown: View {
    
    var language:UiLanguage
    var isOpen:Bool
    var selectLanguage:(UiLanguage) -> Void
    
    var body: some View {
        Menu{
            
            VStack{
                ForEach(
                    UiLanguage.companion.allLanguages,
                    id: \.language.langCode
                ){ langauge in
                    LanguageDropDownItem(
                        language: langauge
                    ) {
                         selectLanguage(langauge)
                    }
                }
            }
        } label: {
            HStack{
                SmallLangaugeIcon(langauge: language)
                Text(language.language.langName)
                    .foregroundStyle(Color.lightBlue)
                Image(systemName: isOpen ? "chevron.up" : "chevron.down")
                    .foregroundStyle(Color.lightBlue)
            }
        }
    }
    
}

#Preview {
    LanguageDropDown(
        language: UiLanguage(language: .english, imageName:"english"),
        isOpen:true, 
        selectLanguage: {lang in
        }
    )
}
