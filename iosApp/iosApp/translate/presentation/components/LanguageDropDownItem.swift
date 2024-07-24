//
//  LanguageDropDownItem.swift
//  iosApp
//
//  Created by Anjali Prajapati on 09/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LanguageDropDownItem: View {
    var language: UiLanguage
    var onClick: () -> Void
    var body: some View {
        Button(action: onClick){
            HStack{
                if let image = UIImage(named: language.imageName.lowercased()) {
                    Image(uiImage:image)
                        .resizable()
                        .frame(width: 40,height: 40)
                        .padding(.trailing,5)
                    Text(language.language.langName)
                        .foregroundStyle(Color.textBlack)
                }
            }
        }
    }
    
}

#Preview {
    LanguageDropDownItem(
        language: UiLanguage(language: .english, imageName:"english"),
        onClick: {}
    )
}
