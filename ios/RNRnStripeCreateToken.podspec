
Pod::Spec.new do |s|
  s.name         = "RNRnStripeCreateToken"
  s.version      = "1.0.0"
  s.summary      = "RNRnStripeCreateToken"
  s.description  = <<-DESC
                  RNRnStripeCreateToken
                   DESC
  s.homepage     = ""
  s.license      = "MIT"
  # s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
  s.author             = { "author" => "author@domain.cn" }
  s.platform     = :ios, "7.0"
  s.source       = { :git => "https://github.com/author/RNRnStripeCreateToken.git", :tag => "master" }
  s.source_files  = "RNRnStripeCreateToken/**/*.{h,m}"
  s.requires_arc = true


  s.dependency "React"
  #s.dependency "others"

end

  